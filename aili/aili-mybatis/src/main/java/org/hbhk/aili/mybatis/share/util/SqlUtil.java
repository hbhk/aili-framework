package org.hbhk.aili.mybatis.share.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.hbhk.aili.mybatis.server.annotation.Column;
import org.hbhk.aili.mybatis.server.annotation.Id;

/**
 * sql辅助为类
 */
public class SqlUtil {

	public static Field[] getColumnFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Column col = field.getAnnotation(Column.class);
			if (col != null) {
				list.add(field);
			}
		}
		if (clazz.getSuperclass() != null) {
			Class<?> superClass = clazz.getSuperclass();
			fields = superClass.getDeclaredFields();
			for (Field field : fields) {
				Column col = field.getAnnotation(Column.class);
				if (col != null) {
					list.add(field);
				}
			}
		}
		return list.toArray(new Field[] {});
	}



	public static String getpriKey(Field[] fields) {
		String pk= null;
		for (Field field : fields) {
			Id id = field.getAnnotation(Id.class);
			if (id != null) {
				pk =  field.getAnnotation(Column.class).value();
			}
		}
		if(pk == null){
			throw new RuntimeException("未找到主键");
		}
		return pk;

	}


}
