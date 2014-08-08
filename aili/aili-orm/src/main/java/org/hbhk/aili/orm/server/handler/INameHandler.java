package org.hbhk.aili.orm.server.handler;

import org.hbhk.aili.orm.server.annotation.Column;

/**
 * 名称处理接口
 */
public interface INameHandler {

	/**
	 * 根据实体名获取表名
	 * 
	 * @param entityName
	 * @return
	 */
	String getTableName(Class<?> cls);

	/**
	 * 根据表名获取主键名
	 * 
	 * @param entityName
	 * @return
	 */
	String getPrimaryName(Class<?> cls);

	/**
	 * 根据属性名获取列名
	 * 
	 * @param fieldName
	 * @return
	 */
	String getColumnName(Class<?> cls, String fieldName);

	Column getColumn(Class<?> cls, String fieldName);
}