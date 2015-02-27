package org.hbhk.aili.rpc.server.hessian;

import com.caucho.hessian.server.HessianServlet;

public class BasicService extends HessianServlet implements IBasic {
	private static final long serialVersionUID = 7299340854234925425L;

	public String hello(String str) {
		return "Hello, world";
	}
}
