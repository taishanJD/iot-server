package com.quarkdata.data.dal.rest.quarkshare;

import com.quarkdata.data.util.PropertiesUtils;

public class QuarkShareApiConstants {

    /**
     * quark share api base path
     */
    public static String apiBasePath = PropertiesUtils.prop.get("quarkShareApiBasePathInternal");

    /**
     * 登录 获取token
     */
    static String login =  "/internal/login";

    /**
     * 验证token
     */
    static String validate_token = "/internal/validate_token";
   
}
