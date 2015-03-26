package org.hbhk.aili.mybatis.server;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.share.util.BeanToMapUtil;
import org.hbhk.aili.mybatis.server.dao.IUserDao;
import org.hbhk.aili.mybatis.server.model.UserInfo;
import org.hbhk.aili.mybatis.server.support.Page;
import org.hbhk.aili.mybatis.server.support.Pagination;
import org.hbhk.aili.mybatis.server.support.Sort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
@Transactional
public class AppTest {

	private Log log = LogFactory.getLog(getClass());
	@Autowired
	IUserDao userDao;

	@Test
	public void test() {
		try {
			log.info("1111111111");
			Map<String, Object> params = new HashMap<String, Object>();
			UserInfo query = new UserInfo();
			// query.setName("111asdas");
			BeanToMapUtil.convert(query, params);
			Long s = System.currentTimeMillis();
			List<UserInfo> user = userDao.get(params);
			Long e = System.currentTimeMillis();
			// 166
			System.out.println("pppppppppppppp:" + (e - s) + user);
			// System.out.println(user.size()+user.get(0).getCreatUser()+user.get(0).getName()+user.get(0).getCreateTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testById() {
		try {
			log.info("1111111111");
			Map<String, Object> params = new HashMap<String, Object>();
			UserInfo query = new UserInfo();
			// query.setName("111asdas");
			BeanToMapUtil.convert(query, params);
			Long s = System.currentTimeMillis();
			UserInfo user = userDao.getById(1L);
			Long e = System.currentTimeMillis();
			// 166
			System.out.println("pppppppppppppp:" + (e - s) + ":"
					+ user.getName());
			// System.out.println(user.size()+user.get(0).getCreatUser()+user.get(0).getName()+user.get(0).getCreateTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getPagetest() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			UserInfo query = new UserInfo();
			// query.setId(1l);
			query.setName("hbhk");
			BeanToMapUtil.convert(query, params);
			List<UserInfo> user = userDao.getPage(params, 2, 2);
			System.out.println(user.size() + "" + user.get(0).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getPagetest1() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			UserInfo query = new UserInfo();
			// query.setId(1l);
			query.setName("hbhk");
			BeanToMapUtil.convert(query, params);
			Page page = new Page();
			page.setPageNum(2);
			page.setPageSize(20);
			Pagination<UserInfo> user = userDao.getPagination(params, page,new Sort("aaa"));
			System.out.println(user.getDatas());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getPagetest2() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			UserInfo query = new UserInfo();
			// query.setId(1l);
			query.setName("hbhk");
			BeanToMapUtil.convert(query, params);
			Page page = new Page();
			page.setPageNum(2);
			page.setPageSize(2);
			Pagination<UserInfo1> user = userDao.getCustomPagination(params,
					page,new Sort("name"));
			for (UserInfo1 iterable_element : user.getDatas()) {
				System.out.println("id:" + iterable_element.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getPagetestCount() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			UserInfo query = new UserInfo();
			// query.setId(1l);
			query.setName("hbhk");
			BeanToMapUtil.convert(query, params);
			int user = userDao.getPageTotalCount(params);
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	@Rollback(true)
	@Transactional()
	public void insert() {
		try {
			UserInfo user = new UserInfo();
			user.setName("hbhk");
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setCreatUser("hbhk");
			// user.setUpdateUser("hbhk");
			userDao.insert(user);
			System.out.println(user.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void update() {
		try {
			UserInfo user = new UserInfo();
			user.setName("222");
			user.setId(3l);
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