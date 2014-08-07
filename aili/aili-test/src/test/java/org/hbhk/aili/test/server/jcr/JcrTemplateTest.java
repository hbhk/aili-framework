package org.hbhk.aili.test.server.jcr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JcrTemplateTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jcr-spring.xml");

		/*JcrTemplate template = context.getBean(JcrTemplate.class);
		template.addLockToken("df");*/

	}

}
