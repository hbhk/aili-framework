package org.hbhk.aili.mybatis.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.mybatis.server.dao.IPersonDao;
import org.hbhk.aili.mybatis.server.model.Order;
import org.hbhk.aili.mybatis.server.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
@Transactional
public class PersonTest {

	private Log log = LogFactory.getLog(getClass());
	@Autowired
	IPersonDao personDao;

	@Test
	public void test() {
		try {
			Person p =  personDao.selectPersonFetchOrder(1L);
			System.out.println(p.getOrderList().size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	
	}
	
	@Test
	public void test1() {
		try {
			Order o =  personDao.selectOrdersFetchPerson(1L);
			System.out.println(o.getPrice());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	
	}
	

}