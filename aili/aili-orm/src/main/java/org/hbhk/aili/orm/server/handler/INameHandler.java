package org.hbhk.aili.orm.server.handler;


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
	String getTableName(String  tableName);

	/**
	 * 根据表名获取主键名
	 * 
	 * @param entityName
	 * @return
	 */
	String getPrimaryName(String primaryName);

	/**
	 * 根据属性名获取列名
	 * 
	 * @param fieldName
	 * @return
	 */
	String getColumnName(String fieldName);

}