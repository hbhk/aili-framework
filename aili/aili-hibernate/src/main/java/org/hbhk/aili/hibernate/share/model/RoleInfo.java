package org.hbhk.aili.hibernate.share.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name= "t_aili_role")
public class RoleInfo implements java.io.Serializable {

	private static final long serialVersionUID = 4218930427867063297L;
	@Column(name="id",length=255)
	private String id;
	@Column(name="id",length=255)
	private String code;
	@Column(name="id",length=255)
	private boolean enable;
	@Column(name="id",length=255)
	private String name;
	
	private Set<ResourceInfo> resources;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ResourceInfo> getResources() {
		return resources;
	}

	public void setResources(Set<ResourceInfo> resources) {
		this.resources = resources;
	}

}