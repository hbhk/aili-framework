package org.hbhk.aili.job;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleJobTest1 {

	
	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:job/jobContext.xml");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
