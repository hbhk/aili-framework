package org.hbhk.aili.mybatis.share.model;

import java.io.Serializable;

import org.hbhk.aili.mybatis.server.annotation.Column;
import org.hbhk.aili.mybatis.server.annotation.Id;

public class BaseModel implements Serializable {
	private static final long serialVersionUID = 5009300140634580156L;
	@Column("id")
	@Id
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

}
