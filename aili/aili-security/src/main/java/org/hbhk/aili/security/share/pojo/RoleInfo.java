package org.hbhk.aili.security.share.pojo;

import java.util.Set;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Entity;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.share.model.BaseInfo;

@Entity
@Tabel("t_aili_role")
public class RoleInfo extends BaseInfo { 

	private static final long serialVersionUID = 4218930427867063297L;
	@Column("code")
	private String code;
	@Column("enable")
	private int enable;
	@Column("name")
	private String name;
	private Set<ResourceInfo> resources;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
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