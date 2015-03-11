package org.hbhk.aili.support.server.excel.poi.exception;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface ErrorCode {
	public static final int WRONG_DATA_NULL = 1;
	public static final int UNSUPPORTING_DATA_TYPE = 2;
	public static final int OUT_OF_CHOICES = 3;
	public static final int WRONG_DATA_TYPE_STRING = 10;
	public static final int WRONG_DATA_TYPE_NUMBER = 11;
	public static final int WRONG_DATA_TYPE_BOOLEAN = 12;
	public static final int WRONG_DATA_TYPE_DATE = 13;
	public static final int WRONG_DATA_FORMAT = 50;
}
