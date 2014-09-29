package org.hbhk.aili.security.share.pojo;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Entity;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.share.model.BaseInfo;

@Tabel("t_aili_login_log")
@Entity
public class LoginLogInfo extends BaseInfo {

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
