package org.hbhk.aili.nosql.share.util;


public class PaginationUtil {

	public static final int DEFAULT_PAGESIZE = 100;
	public static final int MAX_PAGESIZE = 10000;
	
	private PaginationUtil() {}
	
	/**
	 * 计算出分页的起始索引
	 */
	public static int getStartIndex(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
	
	/** 超过100，将按100处理 */
	public static int dealMaxPageSize(int pageSize) {
		if (pageSize > MAX_PAGESIZE) {
			return MAX_PAGESIZE;
		}
		
		return pageSize;
	}
	
	/**
	 * 计算出分页的页码
	 */
	public static int getPageNo(int startIndex, int pageSize) {
		return (startIndex / pageSize) + 1;
	}
	
}
