package com.quarkdata.data.util.sdk;

import com.quarkdata.data.util.sdk.Utilities.Json.JSONObject;

import java.util.TreeMap;

/**
 * Created by wu on 2016-11-17.
 */
public class RootcloudApiDemo {

    public static void main(String[] args) {
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        //设置密钥
        config.put("secretId", "QDIDe86853682e1343fc84e1235e815a3bc3");
        config.put("secretKey", "0911e23328ec4172b0fe365f3603ccc3");
        //设置url
        String url = "http://localhost:8180";
        QuarkdataApiModuleCenter moduleRootcloud = QuarkdataApiModuleCenter.getInstance(url, config);
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        //设置请求参数
        params.put("deviceId", 167);
        params.put("startTime", "2018-02-01 09:00:00");
        params.put("endTime", "2018-02-13 12:00:00");
        params.put("dataPointNames", "chswd,chsll");
        try {
            //调用接口，设置action
            String testApi = moduleRootcloud.call("/auth/device/device_data", params, "GET");
            //解析返回结果
            JSONObject jsonObject = new JSONObject(testApi);
            System.out.println(jsonObject);
        } catch (Exception e) {
            System.out.println("error..." + e.getMessage());
        }
    }
}
