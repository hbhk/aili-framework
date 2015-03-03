package org.hbhk.aili.support.server.excel.poi.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hbhk.aili.support.server.excel.poi.ReadStatus;


public class DefaultReadStatus implements ReadStatus, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 567602698187676255L;
	
	private int status = STATUS_SUCCESS;
	private String message;
	private List<Exception> exceptions = new ArrayList<Exception>();
	
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
	public List<Exception> getExceptions() {
		return exceptions;
	}
	public void setExceptions(List<Exception> exceptions) {
		this.exceptions = exceptions;
	}
	public void addException(Exception exception){
		this.exceptions.add(exception);
	}
}
