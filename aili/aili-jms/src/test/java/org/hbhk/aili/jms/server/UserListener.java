package org.hbhk.aili.jms.server;

import org.hbhk.aili.jms.server.process.IProcess;
import org.hbhk.aili.jms.share.pojo.JmsHeader;

public class UserListener implements IProcess<UserInfo> {

	@Override
	public Object process(UserInfo req,JmsHeader header) {
		System.out.println(req.getId());
		return req;
	}

}
