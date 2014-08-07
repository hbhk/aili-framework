package org.hbhk.aili.core.share.pojo;

import java.io.Serializable;

public class ResponseEntity implements Serializable {
	private static final long serialVersionUID = -5207285996548676020L;

	private boolean success = true;
	private boolean exception = false;

	private String msg;
	private Object result;
	private String dealUrl;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isException() {
		return exception;
	}

	public void setException(boolean exception) {
		this.exception = exception;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getDealUrl() {
		return dealUrl;
	}

	public void setDealUrl(String dealUrl) {
		this.dealUrl = dealUrl;
	}

}
