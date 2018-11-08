package com.quarkdata.data.dal.rest.quarkshare;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.util.HttpTookit;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ValidateCodeApi {

    private static Logger logger = Logger.getLogger(ValidateCodeApi.class);

    /**
     * 获取验证码图形
     *
     * @return
     *         验证码图片
     */
    public ResultCode<ModelMap> getEncodeImg(){
        Map<String, String> params=new HashMap<>();
        String doGet = HttpTookit.doGet(IOTApiConstants.apiBasePath + IOTApiConstants.get_validate_code, params);
        logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.get_validate_code
                     + " ,params : " + HttpTookit.getParams(params)
                     + " ,result : " + doGet);
        return JSON.parseObject(doGet, new TypeReference<ResultCode<ModelMap>>(){});
    }

    /**
     * 校验验证码
     * @param uuidKey
     *        32位UUID(验证码唯一标识)
     * @param validateCode
     *        用户输入的验证码
     * @return
     *        true / false
     */
    public ResultCode<Boolean> checkValidateCode(String uuidKey, String validateCode){
        Map<String, String> params=new HashMap<>();
        params.put("uuidKey", uuidKey);
        params.put("validateCode", validateCode);
        String doPost = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.check_validate_code, params);
        logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.check_validate_code
                     + " ,params : " + HttpTookit.getParams(params)
                     + " ,result : " + doPost);
        return JSON.parseObject(doPost, new TypeReference<ResultCode<Boolean>>(){});
    }

}
