package com.quarkdata.data.util.sdk.Common;


import com.quarkdata.data.util.sdk.Utilities.MD5;
import com.quarkdata.data.util.sdk.Utilities.SslUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * 请求调用类
 */
public class Request {
	protected static String requestUrl = "";
	protected static String rawResponse = "";
	protected static int timeOut = 5000;//设置连接主机的超时时间，单位：毫秒，可以根据实际需求合理更改 timeOut 的值。

	public static String getRequestUrl() {
		return requestUrl;
	}

	public static String getRawResponse() {
		return rawResponse;
	}

	public static String generateUrl(TreeMap<String, Object> params,
			String secretId, String secretKey, String requestMethod,
			String requestHost) {
		if (!params.containsKey("secretId"))
			params.put("secretId", secretId);

		if (!params.containsKey("nonce"))
			params.put("nonce",
					new Random().nextInt(Integer.MAX_VALUE));

		if (!params.containsKey("timestamp"))
			params.put("timestamp", System.currentTimeMillis() / 1000);

		String[] split = requestHost.split("//");
		String plainText = Sign.makeSignPlainText(params, requestMethod, split[1]);
//		System.out.println("请求参数：" + plainText);
		try {
			String signature = Sign.sign(plainText, secretKey);
			params.put("signature", signature);
//			System.out.println("签名串：" + signature);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (params.get("action").toString().equals("multipartUploadVodFile")) {
			String url = requestHost;
			url += Sign.buildParamStr1(params,requestMethod);
			return url;
		}

		String url = requestHost;
		if (requestMethod.equals("GET") || requestMethod.equals("DELETE") || requestMethod.equals("PUT")) {
			url += Sign.buildParamStr1(params,requestMethod);
		}
		return url;
	}

	public static String send(TreeMap<String, Object> params, String secretId,
			String secretKey, String requestMethod, String requestHost, String fileName) {
		if (!params.containsKey("secretId"))
			params.put("secretId", secretId);

		if (!params.containsKey("nonce"))
			params.put("nonce",
					new Random().nextInt(Integer.MAX_VALUE));

		if (!params.containsKey("timestamp"))
			params.put("timestamp", System.currentTimeMillis() / 1000);

//		params.put("requestClient", version);
		params.remove("signature");
		String[] split = requestHost.split("//");
		String plainText = Sign.makeSignPlainText(params, requestMethod, split[1]);
		try {
			params.put("signature", Sign.sign(plainText, secretKey));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (params.get("action") != null && params.get("action").toString().equals("multipartUploadVodFile")) {
			String url = requestHost;
			return sendMultipartUploadVodFileRequest(url, params,
					requestMethod, fileName);
		}

		String url = requestHost;

		return sendRequest(url, params, requestMethod, fileName);
	}

	public static String sendRequest(String url,
			Map<String, Object> requestParams, String requestMethod,
			String fileName) {
		String result = "";
		BufferedReader in = null;
		String paramStr = "";

		for (String key : requestParams.keySet()) {
			if (!paramStr.isEmpty()) {
				paramStr += '&';
			}
			try {
				paramStr += key + '='
						+ URLEncoder.encode(requestParams.get(key).toString(),"utf-8");
			} catch (UnsupportedEncodingException e) {
				result = "{\"code\":2300,\"data\":\"com.quarkdata.Common.Request:121\",\"msg\":\"api sdk throw exception! "
						+ e.toString() + "\"}";
			}
		}

		try {

			if (requestMethod.equals("GET") || requestMethod.equals("DELETE") || requestMethod.equals("PUT")) {
				if (url.indexOf('?') > 0) {
					url += '&' + paramStr;
				} else {
					url += '?' + paramStr;
				}
			}
			requestUrl = url;
			String BOUNDARY = "---------------------------"
					+ MD5.stringToMD5(
							String.valueOf(System.currentTimeMillis()))
							.substring(0, 15);
			URL realUrl = new URL(url);
			URLConnection connection = null;
			if (url.toLowerCase().startsWith("https")) {
				SslUtils.ignoreSsl();
				HttpsURLConnection httpsConn = (HttpsURLConnection) realUrl.openConnection();
				connection = httpsConn;
			} else {
				connection = realUrl.openConnection();
			}

			((HttpURLConnection) connection).setRequestMethod(requestMethod);
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 设置链接主机超时时间
			connection.setConnectTimeout(timeOut);

			if (requestMethod.equals("POST")) {
				((HttpURLConnection) connection).setRequestMethod("POST");
				// 发送POST请求必须设置如下两行
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestProperty("Content-Type",
						"multipart/form-data; boundary=" + BOUNDARY);
				OutputStream out = new DataOutputStream(
						connection.getOutputStream());
				StringBuffer strBuf = new StringBuffer();
				for (String key : requestParams.keySet()) {
					strBuf.append("\r\n").append("--").append(BOUNDARY)
							.append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\""
							+ key + "\"\r\n\r\n");
					strBuf.append(requestParams.get(key));
				}
				out.write(strBuf.toString().getBytes());
				if (fileName != null) {
					File file = new File(fileName);
					String filename = file.getName();
					String contentType = URLConnection.getFileNameMap()
							.getContentTypeFor(fileName);

					strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(BOUNDARY)
							.append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"entityFile\"; filename=\""
							+ filename + "\"\r\n");
					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

					out.write(strBuf.toString().getBytes());

					DataInputStream ins = new DataInputStream(
							new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = ins.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					ins.close();
				}
				byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
				out.write(endData);
				out.flush();
				out.close();
			}



			// 建立实际的连接
			connection.connect();

			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"code\":2700,\"data\":\"com.quarkdata.Common.Request:228\",\"msg\":\"api sdk throw exception! "
					+ e.toString() + "\"}";
		} finally {
			// 使用finally块来关闭输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				result = "{\"code\":2800,\"data\":\"com.quarkdata.Common.Request:234\",\"msg\":\"api sdk throw exception! "
						+ e2.toString() + "\"}";
			}
		}
		rawResponse = result;
		return result;
	}

	public static String sendMultipartUploadVodFileRequest(String url,
			Map<String, Object> requestParams, String requestMethod,
			String fileName) {
		String result = "";
		BufferedReader in = null;
		String paramStr = "";

		for (String key : requestParams.keySet()) {
			if (!paramStr.isEmpty()) {
				paramStr += '&';
			}
			try {
				paramStr += key + '='
						+ URLEncoder.encode(requestParams.get(key).toString(),"utf-8");
			} catch (UnsupportedEncodingException e) {
				result = "{\"code\":2400,\"data\":\"com.quarkdata.Common.Request:260\",\"msg\":\"api sdk throw exception! "
						+ e.toString() + "\"}";
			}
		}

		try {

			if (url.indexOf('?') > 0) {
				url += '&' + paramStr;
			} else {
				url += '?' + paramStr;
			}

//			System.out.println(url);

			requestUrl = url;
			// String BOUNDARY = "---------------------------" +
			// MD5.stringToMD5(String.valueOf(System.currentTimeMillis())).substring(0,15);
			URL realUrl = new URL(url);
			URLConnection connection = null;
			if (url.toLowerCase().startsWith("https")) {
				SslUtils.ignoreSsl();
				HttpsURLConnection httpsConn = (HttpsURLConnection) realUrl.openConnection();

				connection = httpsConn;
			} else {
				connection = realUrl.openConnection();
			}

			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 设置链接主机超时时间
			connection.setConnectTimeout(timeOut);

			File file = new File(fileName);
			long file_length = (Long) requestParams.get("fileSize");
			OutputStream out = new DataOutputStream(
					connection.getOutputStream());
			DataInputStream ins = new DataInputStream(new FileInputStream(file));
			int offset = ((Integer) requestParams.get("offset")).intValue();
			int dataSize = ((Integer) requestParams.get("dataSize")).intValue();
			if (offset >= file_length) {
				return "{\"code\":3001,\"data\":\"com.quarkdata.Common.Request:312\",\"msg\":\"api sdk throw exception! offset larger than the size of file\"}";
			}
			int skipBytes = ins.skipBytes(offset);
			int page = dataSize / 1024;
			int remainder = dataSize % 1024;
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			byte[] bufferOut2 = new byte[remainder];
			while (page != 0) {
				if ((bytes = ins.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, bytes);
				}
				page = page - 1;
			}
			if ((bytes = ins.read(bufferOut2)) != -1) {
				out.write(bufferOut2, 0, bytes);
			}
			ins.close();
			out.flush();
			out.close();

			// 建立实际的连接
			connection.connect();
			try {
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
			} catch (Exception e) {
				result = "{\"code\":3002,\"data\":\"com.quarkdata.Common.Request:340\",\"msg\":\"api sdk throw exception! protocol doesn't support input or the character Encoding is not supported."
						+ "details: " + e.toString() + "\"}";
				if (in != null) {
					in.close();
				}
				rawResponse = result;
				return result;
			}
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			result = "{\"code\":3000,\"data\":\"com.quarkdata.Common.Request:354\",\"msg\":\"api sdk throw exception! "
					+ e.toString() + "\"}";
		} finally {
			// 使用finally块来关闭输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				result = "{\"code\":3003,\"location\":\"com.quarkdata.Common.Request:363\",\"msg\":\"api sdk throw exception! "
						+ e2.toString() + "\"}";
			}
		}
		rawResponse = result;
		return result;
	}
}
