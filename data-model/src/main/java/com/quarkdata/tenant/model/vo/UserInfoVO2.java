package com.quarkdata.tenant.model.vo;

import java.io.Serializable;
import java.util.Date;

import com.quarkdata.tenant.model.dataobj.User;

/**
 * 构造前端显示的用户信息
 */
public class UserInfoVO2  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String role_code;
    
    private Long id;

    private String delFlag;

    private String name;

    private String email;

    private String mobile;

    private String status;

    private String userType;

	private Date loginDate;
	
	private Long createBy;

    private Date createDate;

    private Long updateBy;

    private Date updateDate;

    public String getRole_code() {
		return role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
    
    public UserInfoVO2(User user, String role_code) {
		super();
		this.role_code = role_code;
		this.id = user.getId();
		this.delFlag = user.getDelFlag();
		this.name = user.getName();
		this.email = user.getEmail();
		this.mobile = user.getMobile();
		this.status = user.getStatus();
		this.userType = user.getUserType();
		this.loginDate = user.getLoginDate();
		this.createBy = user.getCreateBy();
		this.createDate = user.getCreateDate();
		this.updateBy = user.getUpdateBy();
		this.updateDate = user.getUpdateDate();
	}

	public UserInfoVO2(){

	}
}
