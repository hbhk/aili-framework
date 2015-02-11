package org.hbhk.aili.jms.server;

import org.hbhk.aili.jms.server.process.IProcess;

public class UserListener implements IProcess<UserInfo> {

	@Override
	public Object process(UserInfo req) {
		System.out.println(req.getId());
		return req;
	}

}
