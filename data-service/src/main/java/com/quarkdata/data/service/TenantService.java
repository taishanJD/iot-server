package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.tenant.model.dataobj.Tenant;

/**
 * 租户service
 */
public interface TenantService {

    /**
     * 根据secretId获取租户信息
     * @param secretId
     * @return
     */
    ResultCode<Tenant> getTenantBySecretId(String secretId);
}
