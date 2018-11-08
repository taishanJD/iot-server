package com.quarkdata.data.model.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by xuximing on 14-12-12.
 *
 */
public class ResultCode<T> {
    private String msg;
    private Integer code;
    private T data;


    public ResultCode() {
        this.msg = Messages.SUCCESS_MSG;
        this.code = Messages.SUCCESS_CODE;
        this.data = null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    public static ResultCode buildErrorResult(int code,String msg){
    	ResultCode rc =new ResultCode();
    	rc.setCode(code);
    	rc.setMsg(msg);
    	return rc;
    }
}
