package com.quarkdata.tenant.model.vo;


import com.quarkdata.tenant.model.dataobj.Role;
import com.quarkdata.tenant.model.dataobj.User;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 */
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private User user;
    private List<Role> roleList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
