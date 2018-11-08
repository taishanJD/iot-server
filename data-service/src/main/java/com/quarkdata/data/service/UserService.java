package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;
/**
 * 
 * @author 侯雷
 *
 */
public interface UserService {

    /**
     * 获取用户分页列表
     * @param search   模糊搜索内容；如果为空，获取所有数据
     * @param pageNum  当前页码
     * @param pageSize 每页数据条数
     * @return
     */
    ResultCode getList(String search, Integer pageNum, Integer pageSize);
    
    /**
     * 添加用户
     * @param name     用户姓名
     * @param email    邮箱
     * @param mobile   手机号
     * @param roleCode 角色
     * @return
     */
    ResultCode add(String name, String  email, String mobile, String roleCode);
    
    /**
     * 编辑用户
     * @param userId　用户id
     * @param mobile 用户手机号
     * @param roleCode 所在租户下的角色
     * @return
     */
    ResultCode update(Long userId,String mobile,String roleCode);

    /**
     * 编辑用户个人信息
     * @param name 用户姓名
     * @param mobile 用户手机号
     * @return
     */
    ResultCode updateUserInfo(String name, String mobile);

    /**
     * 获得用户详情　（用户角色　所属租户信息）
     * @return
     */
    ResultCode getInfo();

    /**
     * 逻辑删除用户　(update delFlag)
     * @param userId　用户id
     * @return
     */
    ResultCode delete(Long userId);

    /**
     * 更改用户状态　
     * @param usersId　用户id
     * @param status 用户状态　０　１　２
     * @return
     */
	ResultCode updateStatus(Long[] usersId,String status);

    /**
     * 冻结连续登录失败用户　
     *
     * @param userName　用户登录名
     * @param loginIp　用户登录IP
     * @return
     */
	ResultCode loginFreeze(String userName, String loginIp);

    /**
     * 发送用户激活邮件
     * @param userId 用户ID
     * @param email 邮箱
     * @return
     */
    ResultCode resendEmail(Long userId, String email);
    
    /**
     * 用户点击激活链接后，更新用户为激活状态
     * @param key 激活用户邮件链接中的参数key
     * @return
     */
    ResultCode updateUserStatus(String key, String password);
    
    /**
     * 获取当前会话租户信息
     * @return
     */
    ResultCode getTenantInfo();

    /**
     * 重置apikey
     * @return
     */
	ResultCode resetAPIKey();

    /**
     * 根据邮件链接中的key获取用户名
     * @return
     */
	ResultCode getUserName(String key);
	
    /**
     * 获取当前会话用户信息
     * @return
     */
    ResultCode getUserInfo();
    
    /**
     * 修改当前会话个人资料
     * @param userId　用户id
     * @param name 用户姓名
     * @param mobile 用户手机号
     * @return
     */
    ResultCode resetUserInfo(Long userId,String name,String mobile);
    
    /**
     * 修改当前会话用户密码
     * @param userId　用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    ResultCode resetPassword(Long userId,String oldPassword,String newPassword);
}
