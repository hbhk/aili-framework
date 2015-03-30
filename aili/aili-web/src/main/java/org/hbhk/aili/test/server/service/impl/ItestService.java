package org.hbhk.aili.test.server.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
@Service
public class ItestService implements InitializingBean {
	private static final Logger log = LoggerFactory.getLogger(ItestService.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("aaaaaaaaa");		
	}
}

