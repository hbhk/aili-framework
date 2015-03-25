package org.hbhk.aili.security.share.model;

import org.hbhk.aili.mybatis.server.annotation.Column;
import org.hbhk.aili.mybatis.server.annotation.Table;
import org.hbhk.aili.mybatis.share.model.BizBaseModel;

@Table("t_aili_login_log")
public class LoginLogInfo extends BizBaseModel {

	private static final long serialVersionUID = -8810269314527736345L;
	@Column("user")
	private  String user;
	@Column("ip")
	private String ip;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
