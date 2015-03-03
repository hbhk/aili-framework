package org.hbhk.aili.rpc.server.webservice;

import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ServiceServer {
	public static final Logger log = LoggerFactory
			.getLogger(ServiceServer.class);
	
	public static void main(String[] args) throws Exception {
		Service serviceModel = new ObjectServiceFactory().create(IMyService.class);
		IMyService service = (IMyService)
		 new XFireProxyFactory().create(serviceModel, "http://localhost/remote/url");
		System.in.read();
	}
}

