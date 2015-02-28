package org.hbhk.aili.jms.server;

import org.hbhk.aili.jms.server.process.IProcess;
import org.hbhk.aili.jms.share.pojo.ESBHeader;

public class UserListener implements IProcess<UserInfo> {

	@Override
	public Object process(UserInfo req,ESBHeader header) {
		System.out.println(req.getId());
		return req;
	}

}
