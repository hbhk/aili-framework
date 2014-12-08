package org.hbhk.aili.gen.server.test;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.share.model.BaseInfo;

@Tabel("t_mkk_friend")
public class FriendInfo extends BaseInfo {

	private static final long serialVersionUID = 7230893185745055864L;
	
	@Column("friendUser")
	private String friendUser;
	
	public String getFriendUser() {
		return friendUser;
	}
	public void setFriendUser(String friendUser) {
		this.friendUser = friendUser;
	}
	

}
