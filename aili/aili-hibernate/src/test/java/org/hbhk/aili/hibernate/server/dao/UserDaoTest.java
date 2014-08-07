package org.hbhk.aili.hibernate.server.dao;

import javax.annotation.Resource;

import org.hbhk.aili.hibernate.server.service.ICateService;
import org.hbhk.aili.hibernate.share.model.Category;
import org.hbhk.aili.hibernate.share.model.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml" })
public class UserDaoTest {

	@Resource
	ICateService cateService;

	@Test
	public void t1() {
		Category user = new Category();
		user.setDescription("sfsdf");
		user.setId(33);
		user.setName("sdfsdf");
		cateService.save(user);

	}

	@Test
	public void t2() {
		Page<Category> p = cateService.queryForList("from Category c where  c.id= ? ",
				new Integer[] {2}, new Page<Category>());
		for (int i = 0; i < p.getData().size(); i++) {
			System.out.println(p.getData().get(i).getName());
		}
	}

	@Test
	public void t3() {

	}
}
