package org.hbhk.aili.mybatis.share.model;

import java.util.Date;

import org.hbhk.aili.mybatis.server.annotation.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BizBaseModel extends BaseModel {
	private static final long serialVersionUID = 2088264869615355602L;
	public static final Logger log = LoggerFactory
			.getLogger(BizBaseModel.class);
	
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
