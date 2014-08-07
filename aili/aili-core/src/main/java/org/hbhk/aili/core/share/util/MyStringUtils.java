package org.hbhk.aili.core.share.util;

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
