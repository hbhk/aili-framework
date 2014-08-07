package org.hbhk.aili.core.share.pojo;

import java.io.Serializable;

public class ExceptionEntity implements Serializable {

	private static final long serialVersionUID = 1199270479976877234L;
	private String code;
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
