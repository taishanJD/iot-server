package com.quarkdata.data.util.sdk.Common;

import com.quarkdata.data.util.sdk.Utilities.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;


public class Sign {
	// 编码方式
    private static final String CONTENT_CHARSET = "UTF-8";

    // HMAC算法
    private static final String HMAC_ALGORITHM = "HmacSHA1";

    /**
     * @brief 签名
     *
     * @param signStr 被加密串
     * @param secret 加密密钥
     *
     * @return
     */
    public static String sign(String signStr, String secret) 
    		throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException 
    {
        String sig = null;
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(CONTENT_CHARSET), mac.getAlgorithm());
        mac.init(secretKey);
        byte[] hash = mac.doFinal(signStr.getBytes(CONTENT_CHARSET));

        // base64
        //sig = new String(new BASE64Encoder().encode(hash).getBytes());
        //sig = new String(Base64.encodeBase64(hash));
        sig = new String(Base64.encode(hash));

        return sig;
    }
    
    public static String makeSignPlainText(TreeMap<String, Object> requestParams, String requestMethod, String requestHost) {

        String retStr = "";
        retStr += requestMethod;
        retStr += requestHost;
        retStr += buildParamStr1(requestParams, requestMethod);
        
        return retStr;
    }

    protected static String buildParamStr1(TreeMap<String, Object> requestParams, String requestMethod) {
        return buildParamStr(requestParams, requestMethod);
    }

    protected static String buildParamStr(TreeMap<String, Object> requestParams, String requestMethod) {

        String retStr = "";
        for(String key: requestParams.keySet()) {
        	//排除上传文件的参数
            if(requestMethod == "POST" && requestParams.get(key).toString().substring(0, 1).equals("@")){
            	continue;
            }
            if (retStr.length()==0) {
                retStr += '?';
            } else {
                retStr += '&';
            }
            retStr += key + '=' + requestParams.get(key).toString();

        }
        return retStr;
    }

    /**
     * 接口鉴权
     * @param httpServletRequest 请求request
     * @param secretKey 密钥key
     * @return
     */
    public static Boolean validateRequest(HttpServletRequest httpServletRequest, String secretKey){
        TreeMap<String, Object>  requestParams = new TreeMap();
        String requestMethod = "";
        String requestHost = "";
        String requestPath = "";
        // 获取所有参数
        Map<String, String[]> params = httpServletRequest.getParameterMap();
        for (String key : params.keySet()){
            String[] values = params.get(key);
            requestParams.put(key, values[0]);
        }
        requestMethod = httpServletRequest.getMethod();
        requestPath = httpServletRequest.getRequestURI();
        String serverName = httpServletRequest.getServerName();
        int serverPort = httpServletRequest.getServerPort();
        if (serverPort == 80){
            requestHost = serverName;
        } else{
            requestHost = serverName + ":" + serverPort;
        }
        return validateRequest(requestParams, requestMethod, requestHost + requestPath, secretKey);
    }

    /**
     * 接口鉴权
     * @param requestParams 请求参数
     * @param requestMethod 请求方式
     * @param requestHost 请求地址
     * @param secretKey 密钥key
     * @return
     */
    public static Boolean validateRequest(TreeMap<String, Object> requestParams, String requestMethod, String requestHost, String secretKey){
        Boolean flag = false;
        String signature = (String) requestParams.get("signature");
        requestParams.remove("signature");
        String plainText = makeSignPlainText(requestParams, requestMethod, requestHost);
        try {
            String sign = sign(plainText, secretKey);
            if (sign.equals(signature)){
                flag = true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            flag = false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            flag = false;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            return flag;
        }
    }

}
