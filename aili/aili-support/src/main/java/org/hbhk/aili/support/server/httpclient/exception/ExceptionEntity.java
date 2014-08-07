package org.hbhk.aili.support.server.httpclient.exception;

import java.io.Serializable;

/**
 * 异常信息 
 */
public class ExceptionEntity implements Serializable {

	private static final long serialVersionUID = -5561681738721124698L;

	private Integer statusCode;
	
	private String code;
	
	private String msg;

	public ExceptionEntity() { }
	
	/** 异常状态码 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/** 英文简写描述 */
	public String getCode() {
		return code;
	}

	/** 错误信息 */
	public String getMsg() {
		return msg;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "{code=" + code + ",statusCode=" + statusCode +", msg=" + msg + "}";
	}

}
