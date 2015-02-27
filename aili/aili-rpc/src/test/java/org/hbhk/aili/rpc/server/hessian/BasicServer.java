package org.hbhk.aili.rpc.server.hessian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;

public class BasicServer {
	public static final Logger log = LoggerFactory.getLogger(BasicServer.class);

	public static void main(String[] args) throws Exception {
		String url = "http://localhost/hessian/test/basic";

		HessianProxyFactory factory = new HessianProxyFactory();
		IBasic basic = (IBasic) factory.create(IBasic.class, url);

		System.out.println("Hello: " + basic.hello("ss"));
	}
}
