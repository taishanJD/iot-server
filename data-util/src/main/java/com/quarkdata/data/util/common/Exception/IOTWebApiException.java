package com.quarkdata.data.util.common.Exception;

/**
 * IOTWebApiException
 * @author typ
 * 	2018年3月13日
 */
public class IOTWebApiException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String msg;
    private int code;
    
    public IOTWebApiException(int code,String msg){
    	super(msg);
    	this.code=code;
    	this.msg=msg;
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
