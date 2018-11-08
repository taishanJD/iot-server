package com.quarkdata.data.dal.rest.quarkshare;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.util.HttpTookit;
import com.quarkdata.data.util.ResultUtil;


@Repository
public class TriggerApi {
	private static Logger logger = Logger.getLogger(TriggerApi.class);
	
	
	public ResultCode sendEmail(String email,Long productId,Long deviceId,String triggerName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("productId", Long.toString(productId));
		params.put("deviceId", Long.toString(deviceId));
		params.put("triggerName", triggerName);
		String jsonResult = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.trigger_send_mail, params, "utf-8");
		
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.trigger_send_mail
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});
		if (parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}
}
