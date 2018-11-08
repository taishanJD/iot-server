package com.quarkdata.data.dal.rest.quarkshare;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.util.HttpTookit;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LoginApi {

    private static Logger logger = Logger.getLogger(LoginApi.class);

    /**
     * 登录
     *
     * @param userName
     *        登录用户名（邮箱 / 手机号）
     * @param password
     *        登录密码
     * @return
     *        ResultCode
     */
    public ResultCode<String> login(String userName, String password){
        Map<String, String> params=new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);
        String doPost = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.login, params);
        logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.login
                     + " ,params : " + HttpTookit.getParams(params)
                     + " ,result : " + doPost);
        return JSON.parseObject(doPost,new TypeReference<ResultCode<String>>(){});
    }

    /**
     * 登出
     *
     * @param token
     *        token
     * @return
     *        ResultCode
     */
    public ResultCode logout(String token){
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        String doPost = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.logout, params);
        logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.logout
                     + ", params : " + HttpTookit.getParams(params)
                     + ", result : " + doPost);
        return JSON.parseObject(doPost, new TypeReference<ResultCode>(){});
    }
}
