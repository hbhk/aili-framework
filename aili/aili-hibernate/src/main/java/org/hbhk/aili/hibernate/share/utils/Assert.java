package org.hbhk.aili.hibernate.share.utils;

public abstract class Assert {
	
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}
	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}
	
	public static void hasText(String text, String message) {
		if (!hasTextString(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void hasText(String text) {
		hasText(text,
				"[Assertion failed] - this String argument must have text; it must not be <code>null</code>, empty, or blank");
	}
	
	public static boolean hasTextString(String str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0);
	}
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}
}
