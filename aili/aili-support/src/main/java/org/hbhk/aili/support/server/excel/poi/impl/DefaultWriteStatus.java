package org.hbhk.aili.support.server.excel.poi.impl;

import java.io.Serializable;

import org.hbhk.aili.support.server.excel.poi.WriteStatus;


/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class DefaultWriteStatus implements WriteStatus, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5736231901454798780L;

	private int status = STATUS_SUCCESS;
	private String message;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
