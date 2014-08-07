package org.hbhk.aili.test.server.file;

import java.net.URL;

public class ClassloaderTest {
	

	 public static void main(String[] args) {
		
		URL url= ClassloaderTest.class.getClassLoader().getResource("spring.xml");
		System.out.println(url);
	}

}
