package com.quarkdata.data.web.api.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quarkdata.data.dal.rest.quarkshare.FindPasswordApi;
import com.quarkdata.data.model.common.ResultCode;

/**
 * 找回密码
 *
 * @author  JiaLei
 */
@Controller
@RequestMapping(value = "/reset")
public class FindPasswordController {

    @Autowired
    private FindPasswordApi findPasswordApi;

    @ResponseBody
    @RequestMapping("/send_email")
    public ResultCode applyForResetPassword(@RequestParam(value = "userName") String userName) {
        return findPasswordApi.applyForResetPassword(userName);
    }

    @ResponseBody
    @RequestMapping("/get_username")
    public ResultCode<String> getUserName(@RequestParam(value = "key") String key) {
        return findPasswordApi.getUserName(key);
    }

    @ResponseBody
    @RequestMapping("/reset_password")
    public ResultCode resetPassword(@RequestParam(value = "key") String key,
                                    @RequestParam(value = "password") String password) {
        return findPasswordApi.resetPassword(key, password);
    }
}
