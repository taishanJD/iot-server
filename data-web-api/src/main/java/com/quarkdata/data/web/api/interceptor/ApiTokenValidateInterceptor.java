package com.quarkdata.data.web.api.interceptor;

import com.quarkdata.data.dal.api.UserInfoRedis;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.TokenService;
import com.quarkdata.data.util.SpringContextHolder;
import com.quarkdata.data.util.StringUtils;
import com.quarkdata.data.util.common.mapper.JsonMapper;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 验证token
 * @author wujianbo
 */
public class ApiTokenValidateInterceptor implements HandlerInterceptor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenService tokenApi;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        String authorization1 = request.getParameter("Authorization");
        if (StringUtils.isBlank(authorization) && StringUtils.isBlank(authorization1)){
            //没有token
            ResultCode<String> resultCode = new ResultCode<>();
            resultCode.setCode(Messages.API_AUTHENTICATION_FAILED_CODE);
            resultCode.setMsg(Messages.API_AUTHENTICATION_FAILED_MSG);
            sendFailRes(resultCode, response);
            return false;
        }
        String token = StringUtils.isBlank(authorization) ? authorization1 : authorization;
        ResultCode<Map<String, Long>> resultCode = tokenApi.validateToken(token);
        if (resultCode.getCode().equals(Messages.SUCCESS_CODE)){
            UserInfoRedis userInfo = SpringContextHolder.getBean(UserInfoRedis.class);
            userInfo.setUserId(Long.parseLong(resultCode.getData().get("userId")+""));
            userInfo.setTenantId(Long.parseLong(resultCode.getData().get("tenId")+""));
            return true;
        } else {
            logger.warn("token 验证失败{}", JsonMapper.toJsonString(resultCode));
            sendFailRes(resultCode, response);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void sendFailRes(ResultCode resultCode, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.SC_FORBIDDEN);
        response.getWriter().print(JsonMapper.toJsonString(resultCode));
    }
}
