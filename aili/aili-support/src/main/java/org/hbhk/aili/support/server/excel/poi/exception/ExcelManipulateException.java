package org.hbhk.aili.support.server.excel.poi.exception;

import java.util.Arrays;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class ExcelManipulateException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 478553091122313602L;
	private int errorCode;
	
	/**
	 * [SheetNo,Position,CurrentValue,Pattern,ChoiceList]
	 */
	private Object[] args;

	public ExcelManipulateException(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public ExcelManipulateException(int errorCode, Object[] args) {
		this.errorCode = errorCode;
		this.args = args;
	}

	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "ExcelManipulateException[" + this.errorCode + "]" +
			(this.args == null ? "" : Arrays.asList(this.args).toString());
	}
	
	
}
