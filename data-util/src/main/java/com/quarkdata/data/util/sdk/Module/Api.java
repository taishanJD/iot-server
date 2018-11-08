package com.quarkdata.data.util.sdk.Module;

/**
 * 自定义API接口
 *
 * Created by wujianbo on 2017/08/01.
 */
public class Api extends Base {
    /**
     * e.g. url = api.xxx.com 或 192.168.10.11:8080
     * @param url
     */
    public Api(String url){
        serverHost = url;
    }
}
