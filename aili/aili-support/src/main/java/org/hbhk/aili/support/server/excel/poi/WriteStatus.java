package org.hbhk.aili.support.server.excel.poi;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface WriteStatus {
	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_READ_TEMPLATE_FILE_ERROR = 1;
	public static final int STATUS_SETTING_ERROR = 2;
	public static final int STATUS_WRITE_FILE_ERROR = 3;
	public static final int STATUS_SYSTEM_ERROR = 5;
	
	int getStatus();
	void setStatus(int status);
	String getMessage();
	void setMessage(String message);
}
