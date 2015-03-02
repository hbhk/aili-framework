package org.hbhk.aili.rpc.server.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKClientTest {
	public static final Logger log = LoggerFactory
			.getLogger(ZKClientTest.class);
	private static ZkClient zkClient;

	
	public static void main(String[] args) {
		zkClient = new ZkClient("127.0.0.1:2181");
		//zkClient.createPersistent("/hbhk2",true);
		String node = "/hbhk1";
		boolean a = zkClient.exists(node);
		if (a) {
			zkClient.writeData(node, (System.currentTimeMillis()+"-1111"));
			zkClient.writeData("/hbhk2", (System.currentTimeMillis()+"-2222"));
			String bs = zkClient.readData(node);
			System.out.println(new String(bs));
		}

	}
}
