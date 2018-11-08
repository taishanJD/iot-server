package com.quarkdata.data.web.api.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.UserService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 不需要登录可直接访问
 * @author huliang
 *
 */
@Controller
public class UserController2 {
	
	@Autowired
	UserService userService;
	
    @RequestMapping(value = "/update_status",method=RequestMethod.POST)
    public @ResponseBody ResultCode updateUserStatus(
    		@RequestParam(value = "key", required = true) String key,
    		@RequestParam(value = "password") String password) {
        return userService.updateUserStatus(key, password);
    }

    @ResponseBody
    @RequestMapping(value = "/login_freeze",method=RequestMethod.POST)
    public ResultCode loginFreeze(@RequestParam(value = "userName") String userName) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
        return userService.loginFreeze(userName, getLoginIpAddress(request));
    }


    /**
     * 通过HttpServletRequest返回IP地址
     *
     * @param request
     *        HttpServletRequest
     *
     * @return ip String
     */
    private String getLoginIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
