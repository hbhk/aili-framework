package org.hbhk.aili.jms.share.ex;

import java.io.Serializable;

/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class BusinessException extends RuntimeException implements Serializable, IException {
	
	private static final long serialVersionUID = 1937263904748419090L;
	
	protected String errCode;
	
	private String natvieMsg;
	
	private Object[] arguments;

	public BusinessException() {
		super();
	}
	
	public BusinessException(String msg) {
		super(msg);
	}
	
	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public BusinessException(String code, String msg) {
		super(msg);
		this.errCode = code;
	}
	
	public BusinessException(String code, String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = code;
	}
	
	public BusinessException(String code, String msg, String natvieMsg) {
		super(msg);
		this.errCode = code;
		this.natvieMsg = natvieMsg;
	}
	
	public BusinessException(String code, String msg,String natvieMsg, Throwable cause) {
		super(msg, cause);
		this.errCode = code;
		this.natvieMsg = natvieMsg;
	}
	
	public BusinessException(String code,Object... args) {
		super();
		this.errCode = code;
		this.arguments = args;
	}
	
	public BusinessException(String code,String msg, Object... args) {
		super(msg);
		this.errCode = code;
		this.arguments = args;
	}
	
	@Override
	public void setErrorArguments(Object... args) {
		this.arguments = args;
	}
	
	@Override
	public Object[] getErrorArguments() {
		return this.arguments;
	}
	
	@Override
	public String getErrorCode() {
		return this.errCode;
	}
	
	@Override
	public String getNativeMessage() {
		// TODO
		return natvieMsg;
	}
	
}
