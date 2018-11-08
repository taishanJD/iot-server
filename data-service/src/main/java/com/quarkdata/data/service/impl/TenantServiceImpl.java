package com.quarkdata.data.service.impl;

import com.quarkdata.data.dal.rest.quarkshare.TenantApi;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.TenantService;
import com.quarkdata.tenant.model.dataobj.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TenantServiceImpl implements TenantService{

    @Autowired
    private TenantApi tenantApi;

    /**
     * 根据secretId获取租户信息
     *
     * @param secretId
     * @return
     */
    @Override
    public ResultCode<Tenant> getTenantBySecretId(String secretId) {
        return tenantApi.getTenantBySecretId(secretId);
    }
}
