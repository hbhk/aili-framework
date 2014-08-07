package org.hbhk.aili.orm.server.surpport;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JavaType {

	// 字符串
	private static Map<Class<?>, String> strs = new HashMap<Class<?>, String>();
	static {
		strs.put(String.class, "varchar(255)");

	}
	// 数字
	private static Map<Class<?>, String> nums = new HashMap<Class<?>, String>();
	static {
		nums.put(int.class, "int");
		nums.put(Integer.class, "int");
		nums.put(double.class, "double");
		nums.put(Double.class, "double");
		nums.put(Long.class, "double");
		nums.put(long.class, "double");
		nums.put(BigDecimal.class, "double");
		nums.put(float.class, "double");
		nums.put(Float.class, "double");
	}
	// 日期
	private static Map<Class<?>, String> dates = new HashMap<Class<?>, String>();
	static {
		dates.put(Date.class, "timestamp NULL");
	}
	//boolean
	private static Map<Class<?>, String> booleans = new HashMap<Class<?>, String>();
	static {
		booleans.put(boolean.class, "int");
		booleans.put(Boolean.class, "int");
	}
	public static String getDbType(Class<?> javaType) {
		if (strs.containsKey(javaType)) {
			return strs.get(javaType);
		}
		if (nums.containsKey(javaType)) {
			return nums.get(javaType);
		}
		if (dates.containsKey(javaType)) {
			return dates.get(javaType);
		}
		if (booleans.containsKey(javaType)) {
			return booleans.get(javaType);
		}
		return "varchar(255)";

	}
}
