package com.quarkdata.data.util.sdk.Module;

import com.quarkdata.data.util.sdk.Common.Request;

import java.util.TreeMap;

public abstract class Base {
	protected String serverHost = "";
//	protected String serverUri = "/auth";
	protected String secretId = "";
	protected String secretKey = "";
	protected String requestMethod = "GET";

	public void setConfig(TreeMap<String, Object> config) {
		if (config == null){
			return;
		}
		for (String key : config.keySet()) {
			if(key.equals("secretId")){
				setConfigSecretId(config.get(key).toString());
			}
			else if(key.equals("secretKey")){
				setConfigSecretKey(config.get(key).toString());
			}
		}
	}

	public void setConfigSecretId(String secretId) {
		this.secretId = secretId;
	}

	public void setConfigSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getLastRequest() {
		return Request.getRequestUrl();
	}

	public String getLastResponse() {
		return Request.getRawResponse();
	}

	public String generateUrl(String actionName, TreeMap<String, Object> params, String method) {
		if(params == null){
			params = new TreeMap<>();
		}
//		params.put("action", actionName);
		return Request.generateUrl(params, secretId, secretKey, method, serverHost + actionName);
	}
	
	public String call(String actionName, TreeMap<String, Object> params, String method){
		return call(actionName, params, method, null);
	}
	
	public String call(String actionName, TreeMap<String, Object> params, String method, String fileName){
		if(params == null){
			params = new TreeMap<>();
		}
//		params.put("action", actionName);
		String response = Request.send(params, secretId, secretKey, method, serverHost + actionName, fileName);
		return response;
	}
}
