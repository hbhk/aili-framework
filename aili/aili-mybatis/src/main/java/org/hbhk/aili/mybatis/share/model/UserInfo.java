package org.hbhk.aili.mybatis.share.model;

import java.io.Serializable;

public class UserInfo implements Serializable {
	private static final long serialVersionUID = 6767860047837579053L;

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
