package org.hbhk.aili.orm.server.surpport;

public class OrmConfig {
	
	private String sql;
	
	public OrmConfig(String sql) {
		super();
		this.sql = sql;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	

}
