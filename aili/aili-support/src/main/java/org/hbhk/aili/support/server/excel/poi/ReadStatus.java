package org.hbhk.aili.support.server.excel.poi;

import java.util.List;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface ReadStatus {
	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_READ_FILE_ERROR = 1;
	public static final int STATUS_SETTING_ERROR = 2;
	public static final int STATUS_SYSTEM_ERROR = 5;
	public static final int STATUS_DATA_COLLECTION_ERROR = 10;
	
	int getStatus();
	void setStatus(int status);
	String getMessage();
	void setMessage(String message);
	
	List<Exception> getExceptions();
	void setExceptions(List<Exception> exceptions);
	void addException(Exception exception);
}
