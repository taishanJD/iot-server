package com.quarkdata.data.dal.rest;

import com.quarkdata.data.model.common.ResultCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 根云接口
 * Created by xxm on 2017-1-16.
 */
@Repository
public class TestApi {
	
//	@Autowired
//	private ProductMapper productMapper;

    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger(TestApi.class);


	public ResultCode<Object> processPaymentForBusiness(String billNum, String productCode) {
		ResultCode<Object> result=null;
		
//	    	String resultStr = HttpTookit.doPost(url, params);
//			logger.info("request url : "+url
//					+ ", params : " + JsonMapper.toJsonString(params)
//					+ ", result : " + resultStr);
//			result = JSON.parseObject(resultStr, new TypeReference<ResultCode<Object>>(){});			

		return result;
	}
}
