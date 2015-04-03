package org.hbhk.test.ds;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DynDsTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring.xml");
		IUserService us = (IUserService) context.getBean("userService");
		us.getUser();
		//us.getUser1();
	}
}
