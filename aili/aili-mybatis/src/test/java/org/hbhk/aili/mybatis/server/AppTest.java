package org.hbhk.aili.mybatis.server;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

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
			List<UserInfo> user = userDao.get(1L);
			System.out.println(user.size() + ":" + user.get(0).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void insert() {
		try {
			UserInfo user = new UserInfo();
			user.setName("111");
			userDao.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void update() {
		try {
			UserInfo user = new UserInfo();
			user.setName("222");
			user.setId(1l);
			userDao.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static void main(String args[]) throws Exception {
		System.out.println(getGenericInterfaces(IUserDao.class));
	}

	private static Class<?> getGenericInterfaces(Class<?> clazz)
			throws Exception {
		Type[] types = clazz.getGenericInterfaces();
		ParameterizedType pType = (ParameterizedType) types[0];
		return (Class<?>) pType.getActualTypeArguments()[0];
	}

}