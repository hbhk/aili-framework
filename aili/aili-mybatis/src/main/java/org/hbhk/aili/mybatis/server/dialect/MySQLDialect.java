package org.hbhk.aili.mybatis.server.dialect;

/**
 *  MySQL的分页方言 HISTORY ID
 */
public class MySQLDialect extends Dialect {

	private static final String LIMIT = " limit ";
	
	public boolean supportsLimitOffset() {
		return true;
	}

	public boolean supportsLimit() {
		return true;
	}

	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		if (offset > 0) {
			return sql + LIMIT + offsetPlaceholder + "," + limitPlaceholder;
		} else {
			return sql + LIMIT + limitPlaceholder;
		}
	}

	/**
	 * 为mysql 的语句添加limit限制
	 */
	@Override
	public String getLimitString(String sql, int limit) {
		if(!checkLimit(sql)){
			return sql + LIMIT + limit;
		}
		return sql;
	}
	
	/**
	 * 判断sql语句是否已经加了limit限制
	 */
	private boolean checkLimit(String sql){
		boolean isLimited = false;
		if(sql.toLowerCase().indexOf(LIMIT)!=-1){
			isLimited = true;
		}
		return isLimited;
	}
}