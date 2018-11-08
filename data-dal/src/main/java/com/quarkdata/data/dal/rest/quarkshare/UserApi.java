package com.quarkdata.data.dal.rest.quarkshare;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.util.HttpTookit;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.tenant.model.dataobj.User;
import com.quarkdata.tenant.model.vo.UserInfoVO2;

/**
 * 
 * @author 侯雷
 *
 */
@Repository
public class UserApi {
	
	private static Logger logger = Logger.getLogger(LoginApi.class);
	
	/**
	 * 添加用户
	 * @param tenantId 当前会话租户ID
	 * @param userId 当前会话用户ID
	 * @param name     用户名称
	 * @param email    注册邮箱
	 * @param mobile   预留手机
	 * @param roleCode 在租户下的角色
	 * @return
	 */
	public ResultCode add(Long tenantId, Long userId, String name, String email, String mobile, String roleCode) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("tenantId", Long.toString(tenantId));
		params.put("userId", Long.toString(userId));
		params.put("name", name);
		params.put("email", email);
		params.put("mobile", mobile);
		params.put("roleCode", roleCode);
		String jsonResult = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.add, params);
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode<UserInfoVO2>>(){});
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.add
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		if(parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}

	/**
	 * 获取租户下的用户列表
	 * @param tenantId  租户Id
	 * @param search    模糊查询
	 * @param pageNum   
	 * @param pageSize  
	 * @return
	 */
	public ResultCode<ListResult<UserInfoVO2>> getList(Long tenantId,String search, Integer pageNum, Integer pageSize) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("tenantId", Long.toString(tenantId));
		params.put("search", search);
		params.put("pageNum", Integer.toString(pageNum));
		params.put("pageSize", Integer.toString(pageSize));
		String jsonResult = HttpTookit.doGet(IOTApiConstants.apiBasePath + IOTApiConstants.list, params);
		ResultCode<ListResult<UserInfoVO2>> parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode<ListResult<UserInfoVO2>>>(){});
		
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.list
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		if(parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}
	
	/**
	 * 更改用户信息
	 * @param tenantId  租户id 
	 * @param userId    用户id
	 * @param mobile    用户预留手机
	 * @param roleCode  用户的角色信息
	 * @return
	 */
	public ResultCode update(Long tenantId ,Long userId, String mobile, String roleCode) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("tenantId", Long.toString(tenantId));
		params.put("userId", Long.toString(userId));
		params.put("mobile", mobile);
		params.put("roleCode", roleCode);
		String jsonResult = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.update, params);
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});
		
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.update
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);

		if(parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}

	/**
	 * 更改用户个人信息
	 * @param userId    用户id
	 * @param name      用户姓名
	 * @param mobile    用户预留手机
	 * @return
	 */
	public ResultCode updateUserInfo(Long userId, String name, String mobile) {
		Map<String,String> params = new HashMap<>(3);
		params.put("userId", Long.toString(userId));
		params.put("name", name);
		params.put("mobile", mobile);
		String jsonResult = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.update_user_info, params);
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});

		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.update
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		if(parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}

	/**
	 * 根据用户id获取用户详情 包括用户所在租户信息 以及 其角色信息
	 * @param tenantId  租户id
	 * @param userId    用户id
	 * @return
	 */
	public ResultCode getInfo(Long tenantId,Long userId) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("tenantId", Long.toString(tenantId));
		params.put("userId", Long.toString(userId));
		String jsonResult = HttpTookit.doGet(IOTApiConstants.apiBasePath + IOTApiConstants.detail, params, "utf-8");
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode<Map<String,Object>>>(){});

		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.detail
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);

		if(parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}

	/**
	 * 逻辑删除给定id的用户
	 * @param userId 用户id
	 * @return
	 */
	public ResultCode delete(Long userId) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("userId", Long.toString(userId));
		String jsonResult = HttpTookit.doGet(IOTApiConstants.apiBasePath + IOTApiConstants.delete, params, "utf-8");
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});
		
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.delete
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		if(parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}

	/**
	 * 修改用户的状态 
	 * @param userId
	 * @param status   (0:未激活;1:正常;2:冻结)
	 * @return
	 */
	public ResultCode updateStatus(Long[] usersId, String status) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("usersId", JSON.toJSONString(usersId,false));
		params.put("status", status);
		String jsonResult = HttpTookit.doGet(IOTApiConstants.apiBasePath + IOTApiConstants.status, params, "utf-8");
		
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.status
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});
		if (parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}

	/**
	 * 冻结连续登录失败用户　
	 *
	 * @param userName
	 * 		   用户登录名
	 * @return
	 */
	public ResultCode loginFreeze(String userName, String loginIp) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", userName);
		params.put("loginIp", loginIp);
		String jsonResult = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.login_freeze, params, "utf-8");
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.login_freeze
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});
		if (parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}

    /**
     * 发送用户激活邮件
     * @param userId 用户ID
     * @param email 邮箱
     * @return
     */
	public ResultCode resendEmail(Long userId, String email) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", Long.toString(userId));
		params.put("email", email);
		String jsonResult = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.resend_mail, params, "utf-8");
		
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.resend_mail
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});
		if (parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}
	
    /**
     * 用户点击激活链接后，更新用户为激活状态
     * @param key 激活用户邮件链接中的参数key
     * @param password 用户输入的新密码
     * @return
     */
	public ResultCode updateUserStatus(String key, String password) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", key);
		params.put("password", password);
		String jsonResult = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.update_status, params, "utf-8");
		
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.update_status
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});
		if (parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}
	public ResultCode resetAPIKey(Long tenantId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("tenantId", Long.toString(tenantId));
		String jsonResult = HttpTookit.doGet(IOTApiConstants.apiBasePath + IOTApiConstants.reset_api_key, params, "utf-8");
		
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.reset_api_key
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});
		if (parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
	}
	
    /**
     * 根据邮件链接中的key获取用户名
     *
     * @param key
     *        重置密码邮件链接中的key
     */
    public ResultCode<String> getUserName(String key){
        Map<String, String> params=new HashMap<>();
        params.put("key", key);
        String doGet = HttpTookit.doGet(IOTApiConstants.apiBasePath + IOTApiConstants.get_user_name, params);
        logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.get_user_name
                     + " ,params : " + HttpTookit.getParams(params)
                     + " ,result : " + doGet);
        return JSON.parseObject(doGet, new TypeReference<ResultCode<String>>(){});
    }
    
    /**
     * 修改个人资料
     * @param userId　用户id
     * @param name 用户姓名
     * @param mobile 用户手机号
     */
    public ResultCode<String> resetUserInfo(Long userId,String name,String mobile){
        Map<String, String> params=new HashMap<>();
        params.put("userId", Long.toString(userId));
        params.put("name", name);
        params.put("mobile", mobile);
        String jsonResult = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.reset_user_info, params);
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});
		
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.update
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		if(parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
    }
    
    /**
     * 修改密码
     * @param userId　用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    public ResultCode<String> resetPassword(Long userId,String oldPassword,String newPassword){
        Map<String, String> params=new HashMap<>();
        params.put("userId", Long.toString(userId));
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);
        String jsonResult = HttpTookit.doPost(IOTApiConstants.apiBasePath + IOTApiConstants.reset_user_password, params);
		ResultCode parseObject = JSON.parseObject(jsonResult, new TypeReference<ResultCode>(){});
		
		logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.update
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
		
		if(parseObject == null) {
			return ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
		}
		return parseObject;
    }
	
    public ResultCode<List<User>> getUsersByIds(String userIds){
    	Map<String, String> params=new HashMap<>();
        params.put("userIds", userIds);
    	String jsonResult = HttpTookit.doGet(IOTApiConstants.apiBasePath + IOTApiConstants.get_users_by_ids, params);
    	
    	logger.info("request url : " + IOTApiConstants.apiBasePath + IOTApiConstants.get_users_by_ids
                + " ,params : " + HttpTookit.getParams(params)
                + " ,result : " + jsonResult);
    	ResultCode<List<User>> rc = JSON.parseObject(jsonResult, new TypeReference<ResultCode<List<User>>>(){});
    	return rc;
    }
}
