package org.hbhk.aili.nosql.server.pojo;

import org.hbhk.aili.nosql.share.pojo.DBBaseInfo;

import com.alibaba.fastjson.JSON;

public class TestDBObj extends DBBaseInfo {

	private static final long serialVersionUID = -8600216690475717897L;
	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public static void main(String[] args) {
		String json = "{ \"_id\" : {\"$oid\" : \"5358ced82994892552523c58\"} ,\"number\" : 6}";
		json = json.replace("_id", "id");
		json = json.replace("$oid", "oid");
		TestDBObj t = JSON.parseObject(json, TestDBObj.class);
		System.out.println(t.getId().getOid());

	}

}
