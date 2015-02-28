package org.hbhk.aili.jms.server;

import org.hbhk.aili.jms.server.process.IProcess;
import org.hbhk.aili.jms.share.pojo.JmsHeader;

public class UserListener1 implements IProcess<String> {

	@Override
	public Object process(String req,JmsHeader header) {
		System.out.println(req);
		return req;
	}

}
