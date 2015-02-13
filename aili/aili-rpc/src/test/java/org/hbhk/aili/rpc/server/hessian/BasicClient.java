package org.hbhk.aili.rpc.server.hessian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;

public class BasicClient {
	public static final Logger log = LoggerFactory.getLogger(BasicClient.class);

	public static void main(String[] args) throws Exception {
		String url = "http://www.caucho.com/hessian/test/basic";

		HessianProxyFactory factory = new HessianProxyFactory();
		Basic basic = (Basic) factory.create(Basic.class, url);

		System.out.println("Hello: " + basic.hello("ss"));
	}
}
