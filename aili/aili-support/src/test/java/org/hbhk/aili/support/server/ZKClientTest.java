package org.hbhk.aili.support.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ZKClientTest {
	public static final Logger log = LoggerFactory
			.getLogger(ZKClientTest.class);
	private static ZkClient zkClient;

	
	public static void main(String[] args) throws Exception {
		//zkClient.createPersistent("/hbhk2",true);
		final String path = "/hbhk8";
		Long c = System.currentTimeMillis();
		System.out.println(c);
		List<Thread> ts = new  ArrayList<Thread>();
		for (int i = 0; i < 10; i++) {
			zkClient = new ZkClient("127.0.0.1:2181");
			Thread t = new Thread(){
				@Override
				public void run() {
					zkClient.createPersistent(path);
					System.out.println("创建成功");
				}
			};
			ts.add(t);
		}
		Date date = new Date(c+10000);
		while(true){
			if(date.getTime() < System.currentTimeMillis()){
				System.err.println(System.currentTimeMillis());
			}
			if(date.getTime()==System.currentTimeMillis()){
				System.out.println("开始执行");
				for (Thread thread : ts) {
					thread.start();
				}
			}
		}
	}
	
	@Test
	public void testName() throws Exception {
		System.out.println(System.currentTimeMillis());
	}
}
