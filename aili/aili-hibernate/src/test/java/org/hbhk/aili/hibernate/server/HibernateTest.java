package org.hbhk.aili.hibernate.server;

import javax.annotation.Resource;

import org.hbhk.aili.hibernate.share.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml" })
public class HibernateTest {

	@Resource
	private SessionFactory sessionFactory;

	@Test
	public void addUser() {
		try {
			Session session= sessionFactory.openSession();
			User user= new User();
			user.setAge("12");
			user.setId("1");
			user.setUserName("hbhk");
			session.save(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
