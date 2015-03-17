package org.hbhk.aili.test.server.ws;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringUsersWsClient {
	public static final Logger log = LoggerFactory
			.getLogger(SpringUsersWsClient.class);

	public static void main(String[] args) {
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IGetInfoService.class);
		factory.setAddress("http://localhost:8080/CXFWebService/Users");
		IGetInfoService service = (IGetInfoService) factory.create();
	}
}
