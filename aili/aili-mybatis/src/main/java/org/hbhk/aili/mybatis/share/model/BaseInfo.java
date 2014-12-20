package org.hbhk.aili.mybatis.share.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.hbhk.aili.mybatis.server.annotation.Tabel;

public class BaseInfo implements Serializable {
	private static final long serialVersionUID = 5009300140634580156L;
	private Long id;
	private String creatUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
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
	
	public String getById(Long id) {
		String tab = this.getClass().getAnnotation(Tabel.class).value();
		StringBuilder sql = new StringBuilder();
		sql.append("select *from ");
		sql.append(tab);
		sql.append("where id = #{id}");
		return sql.toString();
	}
	
	
	public String deleteById(Long id) {
		String tab = this.getClass().getAnnotation(Tabel.class).value();
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append(tab);
		sql.append("where id = #{id}");
		return sql.toString();
	}
	
	public String updateStatusById(Map<String, Object> para) {
		String tab = this.getClass().getAnnotation(Tabel.class).value();
		StringBuilder sql = new StringBuilder();
		sql.append("update ");
		sql.append(tab);
		sql.append(" set status = #{status} ");
		sql.append("where id = #{id}");
		return sql.toString();
	}

}
