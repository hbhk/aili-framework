package org.hbhk.aili.rpc.server.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboServer {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationProvider.xml" });
		context.start();
		System.in.read();
	}
}
