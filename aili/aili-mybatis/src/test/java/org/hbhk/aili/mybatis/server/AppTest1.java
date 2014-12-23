package org.hbhk.aili.mybatis.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hbhk.aili.core.share.util.BeanToMapUtil;
import org.hbhk.aili.mybatis.server.dao.IUserTestDao;
import org.hbhk.aili.mybatis.share.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
			System.out.println("pppppppppppppp:"+(e-s));
			//System.out.println(user.size()+user.get(0).getCreatUser()+user.get(0).getName()+user.get(0).getCreateTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}