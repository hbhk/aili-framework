package org.hbhk.aili.rpc.server.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HBHKConsumer  {

	public static void run() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationConsumer.xml" });
		context.start();
		IProcessData processData = (IProcessData) context
				.getBean("processData"); // get
											// service
		// proxy
		String hello = processData.deal("hello"); // do invoke!
		System.out.println(Thread.currentThread().getName() + " " + hello);
		
		String ss = processData.deal1(new BasicTableConfigSupport("str"), "data");
		System.out.println(ss);
	}

	public static void main(String[] args) {
		run();
	}
}
