package org.hbhk.aili.orm.server.page;

public interface PageQueryProvider {
	String getPagableQuery(String sql, int begin, int count);
}
