package com.quarkdata.data.dal.rest.quarkshare;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.quarkdata.data.util.HttpTookit;
import com.quarkdata.data.util.common.mapper.JsonMapper;


/**
 * @author typ
 * 	2018年6月20日
 */
@Repository
public class EmailApi {
	private static Logger logger = Logger.getLogger(EmailApi.class);
	
	/**
	 * 
	 * @param receiver
	 * @param emailSubject
	 * @param emailContent
	 * @param emailContentType
     *        邮件内容的类型,
     *        支持纯文本格式的内容:"text/plain;charset=utf-8";
     *        支持HTML格式的内容:"text/html;charset=utf-8"
	 */
	public void sendEmail(String[] receiver, String emailSubject, String emailContent, String emailContentType){
		Map<String,String[]> params = new HashMap<String, String[]>();
		params.put("receiver", receiver);
		params.put("emailSubject", new String[]{emailSubject});
		params.put("emailContent", new String[]{emailContent});
		params.put("emailContentType", new String[]{emailContentType});
		String result = HttpTookit.doPost2(IOTApiConstants.apiBasePath + IOTApiConstants.email_sender, params);
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.email_sender
                + " ,params : " + JsonMapper.toJsonString(params)
                + " ,result : " + result);
		
	}
}
