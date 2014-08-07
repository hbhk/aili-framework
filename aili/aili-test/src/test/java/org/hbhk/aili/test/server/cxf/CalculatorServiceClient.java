package org.hbhk.aili.test.server.cxf;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.hbhk.aili.test.server.ws.impl.GetInfoService;

public class CalculatorServiceClient {
	private String targetURL;

	public CalculatorServiceClient(String targetURL) {
		this.targetURL = targetURL;
	}

	public void invoke() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(GetInfoService.class);
		factory.setAddress(this.targetURL);
		GetInfoService calculatorIntf = (GetInfoService) factory.create();
		// invoking add web servcie
		System.out.println("10 + 15 = " + calculatorIntf.add(10, 15));

	}
	
	public static void main(String[] args) {
		
	}
}
