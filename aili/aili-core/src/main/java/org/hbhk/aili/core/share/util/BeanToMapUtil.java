package org.hbhk.aili.core.share.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class BeanToMapUtil {

	public static Map<String, Object> convert(Object bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (bean == null) {
			throw new RuntimeException("bean对象为空");
		}
		Class<?> cls = bean.getClass();
		Field[] fields = getColumnFields(cls);
		for (Field field : fields) {
			String name = field.getName();
			if(Modifier.isStatic(field.getModifiers()) 
					 || Modifier.isFinal(field.getModifiers())){
				 continue;  
			}
			Object value = null;
			try {
				field.setAccessible(true);
				value = field.get(bean);
			} catch (Exception e) {
				throw new RuntimeException("获取源对象属性的值出错:", e);
			}
			if (value == null) {
				continue;
			}
			map.put(name, value);
		}
		return map;
	}

	public static void convert(Object bean, Map<String, Object> map) {
		if (bean == null) {
			throw new RuntimeException("bean对象为空");
		}
		if (map == null) {
			throw new RuntimeException("map对象为空");
		}
		Class<?> cls = bean.getClass();
		Field[] fields = getColumnFields(cls);
		for (Field field : fields) {
			String name = field.getName();
			if(Modifier.isStatic(field.getModifiers()) 
					 || Modifier.isFinal(field.getModifiers())){
				 continue;  
			}
			Object value = null;
			try {
				field.setAccessible(true);
				value = field.get(bean);
			} catch (Exception e) {
				throw new RuntimeException("获取源对象属性的值出错:", e);
			}
			if (value == null) {
				continue;
			}
			map.put(name, value);
		}

	}
	
	public static Field[] getColumnFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			list.add(field);
		}
		if (clazz.getSuperclass() != null) {
			Class<?> superClass = clazz.getSuperclass();
			fields = superClass.getDeclaredFields();
			for (Field field : fields) {
				list.add(field);
			}
		}
		return list.toArray(new Field[] {});
	}
}
