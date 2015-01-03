package org.hbhk.aili.nosql.server;

import java.net.UnknownHostException;
import java.util.List;

import org.hbhk.aili.nosql.server.custom.MongoDBConfig;
import org.hbhk.aili.nosql.server.custom.MongoDaoSupport;
import org.hbhk.aili.nosql.server.pojo.TestDBObj;
import org.hbhk.aili.nosql.share.pojo.DBBaseInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MongoDaoSupportTest {

	MongoDaoSupport test;

	@Before
	public void be() throws UnknownHostException {
		test = new MongoDaoSupport();
		MongoDBConfig c = new MongoDBConfig();
		c.setDatabase("hbhk");
		test.setDbConfig(c);
	}

	@Test
	public void test1() {
		List<TestDBObj>  yy= test.getDBCursor("hbhk1", 5, 1,  TestDBObj.class);
		TestDBObj  j = yy.get(0);
		System.out.println(j.getId().getOid()+"==="+j.getNumber());
	}
	
	public static void main(String[] args) {
		ApplicationContext  context  = new ClassPathXmlApplicationContext("spring.xml");
		MongoDaoSupport support = (MongoDaoSupport) context.getBean("mongoDaoSupport");
		
		List<TestDBObj>  yy= support.getDBCursor("hbhk1", 5, 1,  TestDBObj.class);
		TestDBObj  j = yy.get(0);
		System.out.println(j.getId().getOid()+"==="+j.getNumber());
		
	}

}
