package org.hbhk.aili.jms.server;

import org.hbhk.aili.jms.server.process.IProcess;
import org.hbhk.aili.jms.share.pojo.ESBHeader;

public class UserListener1 implements IProcess<String> {

	@Override
	public Object process(String req,ESBHeader header) {
		System.out.println(req);
		return req;
	}

}
