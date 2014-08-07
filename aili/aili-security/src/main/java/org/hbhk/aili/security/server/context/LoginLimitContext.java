package org.hbhk.aili.security.server.context;

public class LoginLimitContext {

	private static final ThreadLocal<String> CONTEXT = new ThreadLocal<String>();

	public static String getSessionId() {
		return CONTEXT.get();
	}
	public static void setSessionId(String sessionId) {
		CONTEXT.set(sessionId);
	}

}
