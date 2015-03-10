package org.hbhk.aili.core.share.ex;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1937263904748419090L;

	protected String errCode;

	private String errMsg;

	public BusinessException() {
		super();
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String errCode, String msg) {
		super(msg);
		this.errCode = errCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
