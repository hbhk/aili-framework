package org.hbhk.aili.core.server.context;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class SpObserver {
	private static ThreadLocal<String> local = new ThreadLocal<String>();

	public static void putSp(String sp) {
		local.set(sp);
	}

	public static String getSp() {
		return local.get();
	}
}
