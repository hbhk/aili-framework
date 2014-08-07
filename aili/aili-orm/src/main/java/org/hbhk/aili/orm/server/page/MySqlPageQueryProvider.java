package org.hbhk.aili.orm.server.page;


public class MySqlPageQueryProvider implements PageQueryProvider {

	public String getPagableQuery(String sql, int begin, int count) {
		return sql + " limit " + begin + " , " + count;
	}

}
