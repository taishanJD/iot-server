package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.TenantService;
import com.quarkdata.data.web.api.controller.RouteKey;
import com.quarkdata.tenant.model.dataobj.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(RouteKey.PREFIX_API + RouteKey.TENANT)
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @RequestMapping(value = "/get_tenant_by_secretid", method = RequestMethod.GET)
    public ResultCode<Tenant> getTenantBySecretId(String secretId) {
        return tenantService.getTenantBySecretId(secretId);
    }
}
