package org.hbhk.aili.rpc.server.dubbo;

import org.hbhk.aili.rpc.server.dubbo.callback.ICallbackListener;
import org.hbhk.aili.rpc.server.dubbo.callback.ICallbackService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboClient  {
	static	ClassPathXmlApplicationContext context =null;
	public static void run() {
		context = new ClassPathXmlApplicationContext(
				new String[] { "applicationConsumer.xml" });
	}

	public static void main(String[] args) {
		run();
		testCallback();
	}
	
	public static void testCallback() {
		ICallbackService processData = (ICallbackService) context.getBean("callbackService"); 
		processData.addListener("hbhk", new ICallbackListener() {
			@Override
			public void changed(String msg) {
				  System.out.println("callback:" + msg);
			}
		});
		
		
	}
}
