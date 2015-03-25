package org.hbhk.aili.security.share.model;

import java.util.Set;

import org.hbhk.aili.mybatis.server.annotation.Column;
import org.hbhk.aili.mybatis.server.annotation.Table;
import org.hbhk.aili.mybatis.share.model.BizBaseModel;

@Table("t_aili_role")
public class RoleInfo extends BizBaseModel {

	private static final long serialVersionUID = 4218930427867063297L;
	@Column("code")
	private String code;

	@Column("name")
	private String name;

	@Column("type")
	private String type;

	private Set<String> resourceCodes;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getResourceCodes() {
		return resourceCodes;
	}

	public void setResourceCodes(Set<String> resourceCodes) {
		this.resourceCodes = resourceCodes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}