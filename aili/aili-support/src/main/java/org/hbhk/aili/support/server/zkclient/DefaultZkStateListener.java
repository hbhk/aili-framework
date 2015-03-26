package org.hbhk.aili.support.server.zkclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkStateListener;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 何波
 * @date 2015年3月4日 下午3:36:40
 */
public class DefaultZkStateListener implements IZkStateListener {
	public static final Logger log = LoggerFactory
			.getLogger(DefaultZkStateListener.class);
	// SyncConnected Disconnected
	private static KeeperState state;
	/**
	 * 时间间隔 秒
	 */
	private static final int step = 3;
	
	private static ExecutorService executor = Executors.newCachedThreadPool();

	@Override
	public void handleStateChanged(KeeperState state) throws Exception {
		DefaultZkStateListener.state = state;
		log.warn("state:" + state);
		dealReconnectThread();
	}

	@Override
	public void handleNewSession() throws Exception {
		log.warn("handleNewSession");
		dealReconnectThread();
	}
	private void dealReconnectThread() throws Exception {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					dealReconnect();
				} catch (Exception e) {
					log.error( e.getMessage(),e);
				}
			}
		});
	}
	private void dealReconnect() throws Exception {
		while(state == KeeperState.Disconnected){
			List<IDataListener> dataListeners = ZKListeners.dataListeners;
			if (dataListeners != null) {
				for (IDataListener dataListener : dataListeners) {
					dataListener.handleDataChange(dataListener.getPath(), "");
					dataListener.handleDataDeleted(dataListener.getPath());
				}
			}
			List<IChildListener> childListeners = ZKListeners.childListeners;
			if (childListeners != null) {
				for (IChildListener childListener : childListeners) {
					childListener.handleChildChange(childListener.getPath(),
							new ArrayList<String>());
				}
			}
			TimeUnit.SECONDS.sleep(step);
		}
		
	}
}
