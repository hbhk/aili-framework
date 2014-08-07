package org.hbhk.aili.orm.server.annotation;

public interface ColumnTranslator {
	void setModelClass(Class<?> clazz);
	String toColumnName(String attribute);
}
