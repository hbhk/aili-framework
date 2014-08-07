package org.hbhk.aili.core.server.context;

public class SpObserver {
	private static ThreadLocal<String> local = new ThreadLocal<String>();

	public static void putSp(String sp) {
		local.set(sp);
	}

	public static String getSp() {
		return local.get();
	}
}
