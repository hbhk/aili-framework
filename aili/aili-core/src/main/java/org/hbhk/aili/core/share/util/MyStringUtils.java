package org.hbhk.aili.core.share.util;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class MyStringUtils {
	
	public static  int mulStrCount(String str, String flag) {
		int count = 0;
		int start = 0;
		while (str.indexOf(flag, start) >= 0 && start < str.length()) {
			count++;
			start = str.indexOf(flag, start) + flag.length();
		}
		return count;
	}

}
