package org.hbhk.aili.rpc.server.zkclient;
import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class ZKSubTest {
	
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "zk-client.xml" });
		context.start();
		System.in.read();
	}
}

