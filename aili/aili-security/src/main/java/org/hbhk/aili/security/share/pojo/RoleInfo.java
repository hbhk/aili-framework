package org.hbhk.aili.security.share.pojo;

import java.util.Set;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.share.model.BaseInfo;

@Tabel("t_aili_role")
public class RoleInfo extends BaseInfo {

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