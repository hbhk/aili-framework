package org.hbhk.aili.cache.mem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:memcached-spring.xml")
public class MemcachedTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private UserDao dao;
	
	@Test
	public void save(){
		User user= new User();
		user.setUserId("1");
		dao.saveUser(user);
	}
	
	@Test
	public void get(){
		dao.getById("1","2");
	}
	
	@Test
	public void update(){
		
	}
	
	@Test
	public void delete(){
		
	}

	public UserDao getDao() {
		return dao;
	}

	public void setDao(UserDao dao) {
		this.dao = dao;
	}

}


