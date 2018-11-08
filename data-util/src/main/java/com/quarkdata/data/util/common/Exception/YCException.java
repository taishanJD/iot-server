package com.quarkdata.data.util.common.Exception;

/**
 * Created by xiexl on 2018/1/11.
 */
public class YCException extends  RuntimeException{
    private static final long serialVersionUID = 1L;
    private String msg;
    private int code ;
    public YCException(String msg)
    {
        super(msg);
        this.msg=msg;
    }

    public YCException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public YCException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public YCException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
