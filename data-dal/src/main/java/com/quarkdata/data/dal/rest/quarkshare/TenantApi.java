package com.quarkdata.data.dal.rest.quarkshare;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.util.HttpTookit;
import com.quarkdata.tenant.model.dataobj.Tenant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TenantApi {

    private static Logger logger = Logger.getLogger(LoginApi.class);

    public ResultCode<Tenant> getTenantBySecretId(String secretId){
        Map<String, String> params = new HashMap<>();
        params.put("secretId", secretId);
        String doGet = HttpTookit.doGet(IOTApiConstants.apiBasePath + IOTApiConstants.get_tenant_by_secretid, params);
        logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.get_tenant_by_secretid
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + doGet);
        return JSON.parseObject(doGet, new TypeReference<ResultCode<Tenant>>(){});
    }
}
