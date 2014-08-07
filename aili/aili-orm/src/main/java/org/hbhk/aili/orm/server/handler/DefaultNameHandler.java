package org.hbhk.aili.orm.server.handler;

import java.lang.reflect.Field;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.PrimaryKey;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.springframework.stereotype.Component;

/**
 * 默认名称处理handler
 */
@Component
public class DefaultNameHandler implements INameHandler {

	/**
	 * 根据实体名获取表名
	 */
	@Override
	public String getTableName(Class<?> cls) {
		Tabel tbl = cls.getAnnotation(Tabel.class);
		String tblName = null;
		if (tbl != null) {
			tblName = tbl.value();
		} else {
			tblName = cls.getSimpleName();
		}
		return tblName;
	}

	private String getPrikey(Class<?> cls) {
		Field[] fields = cls.getDeclaredFields();
		PrimaryKey primaryKey = null;
		boolean brk = true;
		Field pri_field = null;
		for (int i = 0; i < fields.length && brk; i++) {
			pri_field = fields[i];
			primaryKey = pri_field.getAnnotation(PrimaryKey.class);
			if (primaryKey != null) {
				brk = false;
			}
		}
		String pri_id = null;
		Column column = null;
		if (primaryKey != null) {
			column = pri_field.getAnnotation(Column.class);
			if (column != null) {
				pri_id = column.value();
			} else {
				pri_id = pri_field.getName();
			}
		}
		return pri_id;

	}

	/**
	 * 根据表名获取主键名
	 */
	@Override
	public String getPrimaryName(Class<?> cls) {
		String pri_id = getPrikey(cls);
		if (pri_id == null) {
			if (cls.getSuperclass() != null) {
				pri_id = getPrikey(cls.getSuperclass());
			}
		}
		if (pri_id == null) {
			throw new RuntimeException("Entity primarykey must have");
		}
		return pri_id;
	}

	/**
	 * 根据属性名获取列名
	 */
	@Override
	public String getColumnName(Class<?> cls, String fieldName) {
		Field[] fields = cls.getDeclaredFields();
		Column column = null;
		boolean brk = true;
		for (int i = 0; i < fields.length && brk; i++) {
			Field field = fields[i];
			if (field.getName().equals(fieldName)) {
				PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
				if (primaryKey != null) {
					return null;
				}
				column = field.getAnnotation(Column.class);
				if (column == null) {
					return null;
				}
			}
		}
		String columnName = null;
		if (column != null) {
			columnName = column.value();
		} else {
			columnName = fieldName;
		}
		return columnName;
	}
}
