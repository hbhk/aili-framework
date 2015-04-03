package org.hbhk.aili.test.server.service.impl;
import org.hbhk.aili.test.server.service.ItestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
@Service
public class TestService implements ItestService, InitializingBean {
	private static final Logger log = LoggerFactory.getLogger(TestService.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("aaaaaaaaa");		
	}
}

