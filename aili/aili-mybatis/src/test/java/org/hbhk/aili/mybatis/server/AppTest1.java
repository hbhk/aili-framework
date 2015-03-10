package org.hbhk.aili.mybatis.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hbhk.aili.core.share.util.BeanToMapUtil;
import org.hbhk.aili.mybatis.server.dao.IUserTestDao;
import org.hbhk.aili.mybatis.server.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AppTest1 {
	@Autowired
	IUserTestDao<UserInfo> userDao;

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
			System.out.println("pppppppppppppp:"+(e-s)+user);
			//System.out.println(user.size()+user.get(0).getCreatUser()+user.get(0).getName()+user.get(0).getCreateTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	//@Rollback(false)
	@Transactional()
	public void insert() {
		try {
			UserInfo user = new UserInfo();
			user.setName("hbhk");
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setCreatUser("hbhk");
			user.setUpdateUser("hbhk");
			userDao.insert(user);
			System.out.println(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}