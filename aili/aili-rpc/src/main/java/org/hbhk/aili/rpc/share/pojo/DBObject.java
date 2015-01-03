package org.hbhk.aili.nosql.share.pojo;

import java.io.Serializable;

public class DBObject  implements Serializable {

	private static final long serialVersionUID = 3699966903009754092L;
	private  String oid;
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	

}
