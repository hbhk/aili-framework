package org.hbhk.aili.nosql.server.pojo;

public class JsonDataDetail {
	private int records = 99;
	private String index = "vps_index1";
	private boolean active = true;
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public String getNdex() {
		return index;
	}
	public void setNdex(String index) {
		this.index = index;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
