package org.hbhk.aili.core.server.context;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24
 * 
 */
public class DataSourceContextHolder {
	private static ThreadLocal<String> dataSourceLocal = new ThreadLocal<String>();

	public static void setDataSourceType(String type) {
		dataSourceLocal.set(type);
	}

	public static String getDataSourceType() {
		return dataSourceLocal.get();
	}

	public static void remove() {
		if(dataSourceLocal.get()!=null){
			dataSourceLocal.remove();
		}
	}
}
