package com.quarkdata.data.util.sdk;

import com.quarkdata.data.util.sdk.Common.Sign;

import java.util.TreeMap;

/**
 * sdk 解密校验示例
 * Created by wujianbo on 2017/08/08.
 */
public class SecretDemo {

    public static void main(String[] args) {
        //请求参数（！！不包含signature）
        TreeMap<String, Object> requestParams = new TreeMap();
        requestParams.put("secretId", "RCIDc5756c536a394dd18e459dc0a17b3b78");
        requestParams.put("timestamp", 1502096594);
        requestParams.put("nonce", 304803752);
        requestParams.put("data", "xxxxxxx");
        requestParams.put("data1", "xxxxxxx1");
        //请求方式（GET/POST）
        String requestMethod = "POST";
        //请求地址（api.xxx.com）
        String requestHost = "api.xx.com/auth";
        //密钥key
        String secretKey = "c9b98cf884d641beaa03756beb067ce0";
        Boolean flag = Sign.validateRequest(requestParams, requestMethod, requestHost, secretKey);
        System.out.println(flag);
        if (flag){
            // TODO: 校验通过
        }else {
            // TODO: 校验失败
        }


    }
}
