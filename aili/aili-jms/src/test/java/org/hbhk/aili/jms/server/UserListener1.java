package org.hbhk.aili.jms.server;

import org.hbhk.aili.jms.server.process.IProcess;

public class UserListener1 implements IProcess {

	@Override
	public Object process(Object req) {
		System.out.println(req);
		return req;
	}

}
