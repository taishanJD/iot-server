package com.quarkdata.data.web.api.controller.ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.web.api.controller.RouteKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quarkdata.data.service.TestService;

/**
 * 
 *
 * Created by xu on 2017-01-16.
 */
@Controller
@RequestMapping(RouteKey.PREFIX_AJAX+"/"+RouteKey.TEST)
public class TestAjaxController {

    @Autowired
    private TestService testService;
    
    static Logger logger = LoggerFactory.getLogger(TestAjaxController.class);

    /**
     * test
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/test1")
    public ResultCode<String> getTest(HttpServletRequest request, HttpServletResponse response){
        ResultCode<String> result = new ResultCode<String>();
        try{

        } catch (Exception e){
            logger.error("get pay channels error", e);
            result.setCode(Messages.API_ERROR_CODE);
            result.setMsg(Messages.API_ERROR_MSG);
            return result;
        }
        return result;
    }

 
}
