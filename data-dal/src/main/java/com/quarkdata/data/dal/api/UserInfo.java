package com.quarkdata.data.dal.api;

import com.quarkdata.tenant.model.dataobj.Tenant;
import com.quarkdata.tenant.model.vo.UserInfoVO;


/**
 * 用户信息 bean，作用域：request
 * @author wujianbo
 */
public interface UserInfo {

    /**
     * 获取当前用户信息
     * @return
     */
    UserInfoVO getUserInfoVO();

    /**
     * 获取当前用户所在企业
     * @return
     */
    Tenant getIncorporation();

}
