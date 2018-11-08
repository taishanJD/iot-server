package com.quarkdata.data.dal.rest.quarkshare;

import com.quarkdata.data.model.common.ResultCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *
 * @author wujianbo
 */
@Repository
public class TokenApi {

    private static Logger logger = Logger.getLogger(TokenApi.class);

    /**
     * 验证token
     * @param token
     * @return
     */
    public ResultCode<Map<String, Long>> validateToken(String token){
/*
        Map<String, String> params=new HashMap<String, String>();
        params.put("token", token);
        String doGet = HttpTookit.doGet(QuarkShareApiConstants.apiBasePath + QuarkShareApiConstants.validate_token, params);
        logger.info("request url : "+QuarkShareApiConstants.apiBasePath + QuarkShareApiConstants.validate_token
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + doGet);
        return JSON.parseObject(doGet,new TypeReference<ResultCode<Map<String,Long>>>(){});*/
        return null;
    }

    /**
     * 登录 获取token
     * @param userName
     * @param password
     * @return
     */
    public ResultCode<String> login(String userName, String password){
       /* Map<String, String> params=new HashMap<String, String>();
        params.put("userName", userName);
        params.put("password", password);
        String doGet = HttpTookit.doGet(QuarkShareApiConstants.apiBasePath + QuarkShareApiConstants.login, params);
        logger.info("request url : "+QuarkShareApiConstants.apiBasePath + QuarkShareApiConstants.login
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + doGet);
        return JSON.parseObject(doGet,new TypeReference<ResultCode<String>>(){});*/
        return new ResultCode<>();
    }

}
