package org.hbhk.aili.nosql.server.pojo;

public class JsonData {

	private String database ="hbhk";
	private  String table ="t_hbhk";
	private JsonDataDetail  detail = new JsonDataDetail();
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public JsonDataDetail getDetail() {
		return detail;
	}
	public void setDetail(JsonDataDetail detail) {
		this.detail = detail;
	}
	
}
