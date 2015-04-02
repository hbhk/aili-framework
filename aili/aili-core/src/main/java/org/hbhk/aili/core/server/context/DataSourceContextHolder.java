package org.hbhk.aili.core.server.context;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class DataSourceContextHolder {
	private static ThreadLocal<String> local = new ThreadLocal<String>();

	public static void setgetDataSourceType(String type) {
		local.set(type);
	}

	public static String getDataSourceType() {
		return local.get();
	}
}
