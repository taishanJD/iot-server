package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.util.common.Logget.RootLoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.UserService;
import com.quarkdata.data.web.api.controller.RouteKey;

/**
 * @author 侯雷 
 */
@Controller
@RequestMapping(RouteKey.PREFIX_API+RouteKey.USER)
public class UserController {
	@Autowired
	UserService userService;
	
    @RequestMapping(value = "/list",method=RequestMethod.GET)
    @ResponseBody
    public ResultCode getList(
    	   @RequestParam(value = "search", required = false) String search, 
    	   @RequestParam(value = "pageNum", required = false) Integer pageNum, 
    	   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
    	ResultCode result = userService.getList(search,pageNum,pageSize);
        return result;
    }

    @RequestMapping(value = "/add",method=RequestMethod.POST)
    public @ResponseBody ResultCode addUser(
    	   @RequestParam(value = "name", required = true) String name,
    	   @RequestParam(value = "email", required = true) String email,
           @RequestParam(value = "mobile", required = false) String mobile,
           @RequestParam(value = "roleCode", required = true) String roleCode) {
        return userService.add(name, email, mobile, roleCode);
    }

    @RequestMapping(value = "/delete",method=RequestMethod.GET)
    public @ResponseBody ResultCode delete(
           @RequestParam(value = "userId", required = true) Long userId) {
        return userService.delete(userId);
    }

    @RequestMapping(value = "/update",method=RequestMethod.POST)
    public @ResponseBody ResultCode update(
           @RequestParam(value = "userId", required = true) Long userId,
           @RequestParam(value = "mobile", required = true) String mobile,
           @RequestParam(value = "roleCode", required = true) String roleCode) {
        return userService.update(userId, mobile,roleCode);
    }

    @RequestMapping(value = "/update_user_info",method=RequestMethod.POST)
    public @ResponseBody ResultCode updateUserInfo(
           @RequestParam(value = "name") String name,
           @RequestParam(value = "mobile") String mobile) {
        return userService.updateUserInfo(name, mobile);
    }

    @RequestMapping(value = "/freeze",method=RequestMethod.GET)
    public @ResponseBody ResultCode updateStatus(
           @RequestParam(value = "usersId", required = true) Long[] usersId,
           @RequestParam(value = "status", required = true) String status) {
        return userService.updateStatus(usersId, status);
    }

    @RequestMapping(value = "/detail",method=RequestMethod.GET)
    public @ResponseBody ResultCode getInfo() {
        return userService.getInfo();
    }

    @RequestMapping(value = "/resend_mail",method=RequestMethod.POST)
    public @ResponseBody ResultCode resendEmail(
    		@RequestParam(value = "userId", required = true) Long userId, 
    		@RequestParam(value = "email", required = true) String email) {
        return userService.resendEmail(userId, email);
    }
    
    @RequestMapping(value = "/tenant_info",method=RequestMethod.GET)
    public @ResponseBody ResultCode getTenantInfo() {
        return userService.getTenantInfo();
    }

    @RequestMapping(value = "/reset_api_key",method=RequestMethod.GET)
    public @ResponseBody ResultCode resetAPIkey() {
        return userService.resetAPIKey();
    }

    @ResponseBody
    @RequestMapping(value ="/get_username",method=RequestMethod.GET)
    public ResultCode getUserName(@RequestParam(value = "key") String key) {
        return userService.getUserName(key);
    }
    
    @RequestMapping(value = "/user_info",method=RequestMethod.GET)
    public @ResponseBody ResultCode getUserInfo() {
        return userService.getUserInfo();
    }
    
    @RequestMapping(value = "/reset_user_info",method=RequestMethod.POST)
    public @ResponseBody ResultCode resetUserInfo(
           @RequestParam(value = "userId", required = true) Long userId,
           @RequestParam(value = "name", required = true) String name,
           @RequestParam(value = "mobile", required = true) String mobile) {
        return userService.resetUserInfo(userId, name,mobile);
    }
    
    @RequestMapping(value = "/reset_password",method=RequestMethod.POST)
    public @ResponseBody ResultCode resetPassword(
           @RequestParam(value = "userId", required = true) Long userId,
           @RequestParam(value = "oldPassword", required = true) String oldPassword,
           @RequestParam(value = "newPassword", required = true) String newPassword) {
        return userService.resetPassword(userId, oldPassword,newPassword);
    }
}
