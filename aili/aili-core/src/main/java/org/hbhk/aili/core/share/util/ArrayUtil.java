package org.hbhk.aili.core.share.util;

import java.lang.reflect.Array;

public class ArrayUtil {

	@SuppressWarnings("unchecked")
	public static <T> T invertArray(T array) {
		int len = Array.getLength(array);
		Class<?> classz = array.getClass().getComponentType();
		Object dest = Array.newInstance(classz, len);
		System.arraycopy(array, 0, dest, 0, len);
		Object temp;
		for (int i = 0; i < (len / 2); i++) {
			temp = Array.get(dest, i);
			Array.set(dest, i, Array.get(dest, len - i - 1));
			Array.set(dest, len - i - 1, temp);
		}
		return (T) dest;
	}
}
