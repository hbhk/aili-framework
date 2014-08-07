package org.hbhk.aili.nosql.share.pojo;

import java.io.Serializable;

public class DBBaseInfo  implements Serializable {

	private static final long serialVersionUID = -4712233760253537936L;
	
	private  DBObject id;

	public DBObject getId() {
		return id;
	}

	public void setId(DBObject id) {
		this.id = id;
	}

	

}
