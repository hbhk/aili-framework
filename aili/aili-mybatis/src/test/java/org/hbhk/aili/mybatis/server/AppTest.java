package org.hbhk.aili.mybatis.server;

import org.hbhk.aili.mybatis.server.dao.IUserDao;
import org.hbhk.aili.mybatis.share.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AppTest {
	@Autowired
	IUserDao userDao;

	@Test
	public void test() {
		try {
			UserInfo user =  userDao.getById(1l);
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}