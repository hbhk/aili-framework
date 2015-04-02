package org.hbhk.aili.cache.server;

import java.io.IOException;

import org.hbhk.aili.cache.server.pub.IRedisMessage;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSendMessage {
	
	private IRedisMessage redisMessage;
	
	public static void main(String[] args) throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:cache/**/SUBContext.xml");
		System.in.read();
	}
	
	@Test
	public void sendMsg() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:cache/**/SUBContext.xml");
		redisMessage = (IRedisMessage) context.getBean("defaultRedisMessage");
		redisMessage.sendMessage("hbhk", "hbhk");
	}

}
