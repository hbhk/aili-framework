package org.hbhk.aili.rpc.server.webservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class MyService  implements IMyService { 
	public static final Logger log = LoggerFactory.getLogger(MyService.class);

	@Override
	public String sayHello(String user) {
		 return "您好，"+user; 
	}
}

