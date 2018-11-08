package com.quarkdata.data.util.sdk;

import com.quarkdata.data.util.sdk.Module.Api;
import com.quarkdata.data.util.sdk.Module.Base;

import java.lang.reflect.Method;
import java.util.TreeMap;

/**
 * @brief 模块调用类
 *
 */
public class QuarkdataApiModuleCenter {

	volatile private static QuarkdataApiModuleCenter instance = null;

	private Base module;

	/**
     * 构造函数
	 * @param url
     * @param secretId
     * @param secretKey
	 */
	public QuarkdataApiModuleCenter(String url, String secretId, String secretKey){
		this.module = new Api(url);
		TreeMap<String, Object> config = new TreeMap<>();
		config.put("secretId", secretId);
		config.put("secretKey", secretKey);
		this.module.setConfig(config);
	}
	
	/**
	 * 构造模块调用类
	 * @param module 实际模块实例
	 * @param config 模块配置参数
	 */
	private QuarkdataApiModuleCenter(Base module, TreeMap<String, Object> config){
		this.module = module;
		this.module.setConfig(config);
	}

	public static QuarkdataApiModuleCenter getInstance(String url, TreeMap<String, Object> config){
		if(instance != null){
			return instance;
		}else{
			synchronized (QuarkdataApiModuleCenter.class) {
				if(instance == null){
					//二次检查
					instance = new QuarkdataApiModuleCenter(new Api(url), config);
				}
			}
		}
		return instance;
	}
	
	/**
	 * 生成Api调用地址
	 * @param actionName 模块动作名称
	 * @param params 模块请求参数
	 * @return Api调用地址
	 */
	public String generateUrl(String actionName, TreeMap<String, Object> params, String method){
		return module.generateUrl(actionName, params, method);
	}
	
	/**
	 * Api调用
	 * @param actionName 模块动作名称
	 * @param params 模块请求参数
	 * @return json字符串
	 * @throws Exception
	 */
	public String call(String actionName, TreeMap<String, Object> params, String method)
	{
		for(Method method1 : module.getClass().getMethods()){
			if(method1.getName().equals(actionName)){
				try {
					return (String) method1.invoke(module, params);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return module.call(actionName, params, method);
	}
	
	public void setConfigSecretId(String secretId) {
		module.setConfigSecretId(secretId);
	}

	public void setConfigSecretKey(String secretKey) {
		module.setConfigSecretKey(secretKey);
	}

}
