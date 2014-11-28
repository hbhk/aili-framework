package org.hbhk.aili.orm.server.handler;

import org.springframework.stereotype.Component;

/**
 * 默认名称处理handler
 */
@Component
public class DefaultNameHandler implements INameHandler {

	/**
	 * 获取表名
	 */
	@Override
	public String getTableName(String tableName) {
		return tableName;
	}

	/**
	 * 获取主键名
	 */
	@Override
	public String getPrimaryName(String primaryName) {

		return primaryName;
	}

	/**
	 * 获取列名
	 */
	@Override
	public String getColumnName(String columnName) {

		return columnName;
	}

}
