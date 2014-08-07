package org.hbhk.aili.orm.server;

import java.util.List;

import org.hbhk.aili.core.server.web.WebApplicationContextHolder;
import org.hbhk.aili.orm.server.test.TestAiliDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring.xml");
		WebApplicationContextHolder.setContext(context);
		TestAiliDao t = (TestAiliDao) context.getBean("testAiliDao");
//		Page p = new Page();
//		p.setSize(5);
//		p.setStart(0);
//		 List<UserInfo> ss =  t.create(1, p);
//		 for (UserInfo userInfo : ss) {
//			System.out.println(userInfo.getUsername());
//		}
		 UserInfo userInfo = new UserInfo();
		 userInfo.setId("22");
		// t.save(userInfo);
		 List<UserInfo> userInfos=  t.get(userInfo);
		 System.out.println(userInfos.get(0).getName());
		 userInfo.setName("uuuuuuu");
		// t.update(userInfo);
		 
	}

}