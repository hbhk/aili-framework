package org.hbhk.aili.orm.share.model;

import java.io.Serializable;
import java.util.Date;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Id;

public abstract class BaseInfo implements Serializable {
	private static final long serialVersionUID = 5009300140634580156L;
	@Id
	@Column("id")
	private Long id;
	@Column("creatUser")
	private String creatUser;
	@Column("createTime")
	private Date createTime;
	@Column("updateUser")
	private String updateUser;
	@Column("updateTime")
	private Date updateTime;
	@Column("status")
	private Integer status = 1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatUser() {
		return creatUser;
	}

	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
