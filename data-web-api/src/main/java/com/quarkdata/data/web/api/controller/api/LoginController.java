package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.web.api.controller.BaseController;
import com.quarkdata.data.dal.rest.quarkshare.LoginApi;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.TokenService;
import com.quarkdata.data.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录
 *
 * @author  JiaLei
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private LoginApi loginApi;
    @Autowired
    private TokenService tokenService;

    @ResponseBody
    @RequestMapping(value = "/login")
    public ResultCode login(@RequestParam(value = "userName") String userName,
                            @RequestParam(value = "password") String password) {
        ResultCode resultCode;
        try {
            resultCode = loginApi.login(userName, password);
        } catch (Exception e) {
            logger.error("登录异常", e);
            resultCode = ResultUtil.error(Messages.API_ERROR_CODE, Messages.API_ERROR_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping("/logout")
    public ResultCode logout(@RequestParam(value = "token") String token){
        return loginApi.logout(token);
    }

    @ResponseBody
    @RequestMapping(value = "/validate_token")
    public ResultCode validateToken(@RequestParam String token) {
        return tokenService.validateToken(token);
    }
}
