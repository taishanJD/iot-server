package com.quarkdata.data.dal.api;

import com.quarkdata.data.model.common.Constants;
import com.quarkdata.tenant.model.dataobj.Tenant;
import com.quarkdata.tenant.model.vo.UserInfoVO;
import com.quarkdata.data.util.JedisUtils;


/**
 * 从 redis中获取当前用户相关 信息
 */
public class UserInfoRedis implements UserInfo {

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
        return (UserInfoVO)JedisUtils.getObject(Constants.ONEIOT_REDIS_PREFIX + Constants.ONEIOT_REDIS_DELIMITER + Constants.ONEIOT_REDIS_USER_INFO_VO + Constants.ONEIOT_REDIS_DELIMITER + userId);
    }

    /**
     * 获取当前用户所在企业
     *
     * @return
     */
    @Override
    public Tenant getIncorporation() {
        return (Tenant) JedisUtils.getObject(Constants.ONEIOT_REDIS_PREFIX + Constants.ONEIOT_REDIS_DELIMITER + Constants.ONEIOT_REDIS_TEN + Constants.ONEIOT_REDIS_DELIMITER + tenantId);
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
