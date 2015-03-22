package org.hbhk.aili.cache.server;

import static org.junit.Assert.*;

import org.hbhk.aili.cache.server.pub.IRedisMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class TestSendMessage {
	
	private IRedisMessage redisMessage;
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:cache/**/cacheContext.xml");
	}
	
	@Test
	public void sendMsg() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:cache/**/cacheContext.xml");
		redisMessage = (IRedisMessage) context.getBean("defaultRedisMessage");
		redisMessage.sendMessage("hbhk", "hbhk");
	}

}
