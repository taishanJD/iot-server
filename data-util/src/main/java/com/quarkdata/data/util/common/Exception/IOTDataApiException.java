package com.quarkdata.data.util.common.Exception;

public class IOTDataApiException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msg;
    private int code;
	public IOTDataApiException(String msg, int code) {
		super();
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
