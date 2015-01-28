package org.hbhk.aili.test.server.rpc;

import org.hbhk.aili.rpc.server.dubbo.IProcessData;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HBHKConsumer implements Runnable {

	@Override
	public void run() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationConsumer.xml" });
		context.start();
		IProcessData processData = (IProcessData) context
				.getBean("processData"); 
		String hello = processData.deal("hello"); 
		System.out.println(Thread.currentThread().getName() + " " + hello);
	}

	public static void main(String[] args) {
		new Thread(new HBHKConsumer()).start();
	}
}
