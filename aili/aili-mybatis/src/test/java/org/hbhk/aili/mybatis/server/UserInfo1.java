package org.hbhk.aili.mybatis.server;

import org.hbhk.aili.mybatis.server.annotation.Column;
import org.hbhk.aili.mybatis.share.model.BaseModel;

public class UserInfo1 extends BaseModel {
	private static final long serialVersionUID = 6767860047837579053L;

	@Column("name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
