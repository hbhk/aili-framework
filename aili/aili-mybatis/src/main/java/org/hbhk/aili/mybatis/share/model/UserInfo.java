package org.hbhk.aili.mybatis.share.model;

import org.hbhk.aili.mybatis.server.annotation.Table;

@Table("t_aili_user")
public class UserInfo extends BaseInfo {
	private static final long serialVersionUID = 6767860047837579053L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
