package org.hbhk.aili.cache.server;


public class RoleInfo implements java.io.Serializable {

	private static final long serialVersionUID = 4218930427867063297L;
	private String id;
	private String code;
	private boolean enable;
	private String name;

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


}