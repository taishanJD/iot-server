package com.quarkdata.data.service.impl;

import com.quarkdata.data.service.UserService;
import org.springframework.stereotype.Service;

import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.dal.rest.quarkshare.UserApi;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.tenant.model.dataobj.Tenant;
import com.quarkdata.tenant.model.dataobj.User;
import com.quarkdata.tenant.model.vo.UserInfoVO;

/**
 * 
 * @author 侯雷
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	private UserApi userApi=new UserApi();
	
	@Override
	public ResultCode getList(String search, Integer pageNum, Integer pageSize) {
		// 获取当前会话租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		return userApi.getList(tenant.getId(), search, pageNum, pageSize);
	}

	@Override
	public ResultCode add(String name, String email, String mobile, String roleCode) {
		// 获取当前会话租户和用户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		UserInfoVO userInfoVO = UserInfoUtil.getUserInfoVO();
		return userApi.add(tenant.getId(),userInfoVO.getUser().getId(),  name, email, mobile, roleCode);
	}

	@Override
	public ResultCode update(Long userId, String mobile, String roleCode) {
		return userApi.update(UserInfoUtil.getIncorporation().getId(), userId, mobile, roleCode);
	}

	@Override
	public ResultCode updateUserInfo(String name, String mobile) {
		return userApi.updateUserInfo(UserInfoUtil.getUserInfoVO().getUser().getId(), name, mobile);
	}

	@Override
	public ResultCode getInfo() {
		return userApi.getInfo(UserInfoUtil.getIncorporation().getId(), UserInfoUtil.getUserInfoVO().getUser().getId());
	}

	@Override
	public ResultCode delete(Long userId) {
		return userApi.delete(userId);
	}

	@Override
	public ResultCode updateStatus(Long[] usersId, String status) {
		return userApi.updateStatus(usersId, status);
	}

	@Override
	public ResultCode loginFreeze(String userName, String loginIp) {
		return userApi.loginFreeze(userName, loginIp);
	}

	@Override
	public ResultCode resendEmail(Long userId, String email) {
		return userApi.resendEmail(userId, email);
	}

	@Override
	public ResultCode updateUserStatus(String key, String password) {
		return userApi.updateUserStatus(key, password);
	}

	@Override
	public ResultCode getTenantInfo() {
		// 获取当前会话租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		return ResultUtil.success(tenant);
	}

	@Override
	public ResultCode resetAPIKey() {
		Tenant tenant = UserInfoUtil.getIncorporation();
		return userApi.resetAPIKey(tenant.getId());
	}

	@Override
	public ResultCode getUserName(String key) {
		return userApi.getUserName(key);
	}

	@Override
	public ResultCode getUserInfo() {
		// 获取当前会话用户信息
		UserInfoVO userInfoVO = UserInfoUtil.getUserInfoVO();
		// 去除会话中用户密码
		User user = userInfoVO.getUser();
		user.setPassword(null);		
		userInfoVO.setUser(user);
		return ResultUtil.success(userInfoVO);
	}

	@Override
	public ResultCode resetUserInfo(Long userId, String name, String mobile) {
		return userApi.resetUserInfo(userId, name, mobile);
	}

	@Override
	public ResultCode resetPassword(Long userId, String oldPassword,
			String newPassword) {
		return userApi.resetPassword(userId, oldPassword, newPassword);
	}
}
