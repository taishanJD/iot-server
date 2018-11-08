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
public class FindPasswordApi {

    private static Logger logger = Logger.getLogger(FindPasswordApi.class);

    /**
     * 请求重置密码,通过之后发送重置邮件
     *
     * @param userName
     *        用户邮箱
     */
    public ResultCode applyForResetPassword(String userName){
        Map<String, String> params=new HashMap<>();
        params.put("userName", userName);
        System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(150000));
        String doPost = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.send_email, params);
        logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.send_email
                     + " ,params : " + HttpTookit.getParams(params)
                     + " ,result : " + doPost);
        return JSON.parseObject(doPost, new TypeReference<ResultCode>(){});
    }

    /**
     * 根据邮件链接中的key获取用户名
     *
     * @param key
     *        重置密码邮件链接中的key
     */
    public ResultCode<String> getUserName(String key){
        Map<String, String> params=new HashMap<>();
        params.put("key", key);
        String doGet = HttpTookit.doGet(IOTApiConstants.apiBasePath + IOTApiConstants.get_username, params);
        logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.get_username
                     + " ,params : " + HttpTookit.getParams(params)
                     + " ,result : " + doGet);
        return JSON.parseObject(doGet, new TypeReference<ResultCode<String>>(){});
    }

    /**
     * 为指定key的用户重置密码
     *
     * @param key
     *        重置密码邮件链接中的key
     * @param password
     *        用户输入的新密码
     */
    public ResultCode resetPassword(String key, String password){
        Map<String, String> params=new HashMap<>();
        params.put("key", key);
        params.put("password", password);
        String doPost = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.reset_password, params);
        logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.reset_password
                     + " ,params : " + HttpTookit.getParams(params)
                     + " ,result : " + doPost);
        return JSON.parseObject(doPost, new TypeReference<ResultCode>(){});
    }
}
