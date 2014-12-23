package org.hbhk.aili.mybatis.server.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hbhk.aili.core.share.util.BeanToMapUtil;
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
			Map<String, Object> params = new HashMap<String, Object>();
			UserInfo  query  = new UserInfo();
			//query.setName("111asdas");
			BeanToMapUtil.convert(query, params);
			Long s = System.currentTimeMillis();
			List<UserInfo> user = userDao.get(params);
			Long e = System.currentTimeMillis();
			//166
			System.out.println("pppppppppppppp:"+(e-s));
			//System.out.println(user.size()+user.get(0).getCreatUser()+user.get(0).getName()+user.get(0).getCreateTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
//	@Test
//	public void getPagetest() {
//		try {
//			Map<String, Object> params = new HashMap<String, Object>();
//			UserInfo  query  = new UserInfo();
//			query.setId(1l);
//			query.setName("222");
//			BeanToMapUtil.convert(query, params);
//			params.put("start", 0);
//			params.put("size", 5);
//			List<UserInfo> user = userDao.getPage(params);
//			System.out.println(user.size()+""+user.get(0).getName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void insert() {
//		try {
//			for (int i = 0; i < 1000; i++) {
//				UserInfo user = new UserInfo();
//				user.setName("hbhk"+i);
//				user.setCreateTime(new Date());
//				user.setUpdateTime(new Date());
//				user.setCreatUser("hbhk"+i);
//				user.setUpdateUser("hbhk"+i);
//				userDao.insert(user);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	@Test
//	public void update() {
//		try {
//			UserInfo user = new UserInfo();
//			user.setName("222");
//			user.setId(3l);
//			userDao.update(user);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}


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