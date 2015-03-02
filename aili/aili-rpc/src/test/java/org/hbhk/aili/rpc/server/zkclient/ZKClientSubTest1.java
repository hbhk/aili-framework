package org.hbhk.aili.rpc.server.zkclient;

import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKClientSubTest1  {
	public static final Logger log = LoggerFactory
			.getLogger(ZKClientSubTest1.class);
	private static ZkClient zkClient;

	public static void main(String[] args) {
		// 127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002
		
		try {
			zkClient = new ZkClient("127.0.0.1:2181");
			String node = "/hbhk1";
			zkClient.subscribeDataChanges(node, new IZkDataListener() {
			    public void handleDataDeleted(String path) throws Exception {
			    	System.out.println("path:"+path);
			    }
			    @Override
				public void handleDataChange(String dataPath, Object data)
						throws Exception {
					System.out.println("path:"+dataPath);
			    	System.out.println("data:"+data);
					
				}
			});
			zkClient.subscribeDataChanges("/hbhk2", new IZkDataListener() {
			    public void handleDataDeleted(String path) throws Exception {
			    	System.out.println("path:"+path);
			    }
			    @Override
				public void handleDataChange(String dataPath, Object data)
						throws Exception {
					System.out.println("path:"+dataPath);
			    	System.out.println("data:"+data);
					
				}
			});
			zkClient.subscribeStateChanges(new IZkStateListener() {
				
				@Override
				public void handleStateChanged(KeeperState state) throws Exception {
					System.out.println("State:"+state);
				}
				
				@Override
				public void handleNewSession() throws Exception {
					System.out.println("handleNewSession");
				}
			});
			int nums = zkClient.numberOfListeners();
			System.out.println("nums:"+nums);
			TimeUnit.HOURS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		
	}
}
