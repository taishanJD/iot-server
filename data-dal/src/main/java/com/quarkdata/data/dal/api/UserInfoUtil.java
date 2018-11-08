package com.quarkdata.data.dal.api;

import com.quarkdata.tenant.model.dataobj.Tenant;
import com.quarkdata.tenant.model.vo.UserInfoVO;
import com.quarkdata.data.util.SpringContextHolder;

public class UserInfoUtil {

    private static UserInfo getUserInfo(){
        return SpringContextHolder.getBean(UserInfoRedis.class);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserInfoVO getUserInfoVO() {
        return getUserInfo().getUserInfoVO();
    }

    /**
     * 获取当前用户所在企业
     *
     * @return
     */
    public static Tenant getIncorporation() {
        return getUserInfo().getIncorporation();
    }

}
