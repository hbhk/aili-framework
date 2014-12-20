package org.hbhk.aili.core.share.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.atomikos.beans.PropertyUtils;

public class BeanToMapUtil {

	public static Map<String, Object> convert(Object bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (bean == null) {
			throw new RuntimeException("bean对象为空");
		}
		Class<?> cls = bean.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			if ("serialVersionUID".equals(name)) {
				continue;
			}
			if(Modifier.isStatic(field.getModifiers()) 
					 || Modifier.isFinal(field.getModifiers())){
				 continue;  
			}
			Object value = null;
			try {
				value = PropertyUtils.getProperty(bean, name);
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
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			if ("serialVersionUID".equals(name)) {
				continue;
			}
			if(Modifier.isStatic(field.getModifiers()) 
					 || Modifier.isFinal(field.getModifiers())){
				 continue;  
			}
			Object value = null;
			try {
				value = PropertyUtils.getProperty(bean, name);
			} catch (Exception e) {
				throw new RuntimeException("获取源对象属性的值出错:", e);
			}
			if (value == null) {
				continue;
			}
			map.put(name, value);
		}

	}
}
