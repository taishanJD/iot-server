package com.quarkdata.data.dal.api;


import com.quarkdata.tenant.model.dataobj.Tenant;
import com.quarkdata.tenant.model.vo.UserInfoVO;

/**
 * 用户信息 bean，作用域：request
 * @author wujianbo
 */
public class UserInfoDefault implements UserInfo{

    /**
     * 用户id
     */
    private long userId;

    /**
     * 企业id
     */
    private long tenantId;


    /**
     * 获取当前用户信息
     *
     * @return
     */
    @Override
    public UserInfoVO getUserInfoVO() {
        return null;
    }

    /**
     * 获取当前用户所在企业
     *
     * @return
     */
    @Override
    public Tenant getIncorporation() {
        return null;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }
}
