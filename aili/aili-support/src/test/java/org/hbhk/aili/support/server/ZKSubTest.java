package org.hbhk.aili.support.server;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ZKSubTest {
	static ClassPathXmlApplicationContext context;

	public static void main(String[] args) throws IOException {
		context = new ClassPathXmlApplicationContext(
				new String[] { "zk-client.xml" });
		System.in.read();
	}
}
