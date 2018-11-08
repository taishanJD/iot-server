package com.quarkdata.data.util.common.Exception;

import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiexl on 2018/1/11.
 * 自定义异常处理
 */

@ControllerAdvice
public class YCExceptionHandler {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 处理自定义异常
     */
    @ResponseBody
    @ExceptionHandler(YCException.class)
    public ResultCode handleRRException(YCException e){
        logger.error("统一异常处理类",e);
        return  ResultUtil.error(e.getCode(),e.getMsg());
    }


    /**
     * 处理文件操作权限异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(YCAuthorizationException.class)
    public ResultCode handleYCAuthorizationException(YCAuthorizationException e){
        logger.error("统一异常处理类",e);
        return  ResultUtil.error(Messages.API_AUTHEXCEPTION_CODE,Messages.API_AUTHEXCEPTION_MSG);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public  ResultCode  handleException(Exception e){
       logger.error("统一异常处理类",e);
       return  ResultUtil.error(Messages.API_ERROR_CODE,Messages.API_ERROR_MSG);
    }
}
