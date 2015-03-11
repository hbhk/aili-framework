package org.hbhk.aili.support.server.zkclient;
import org.I0Itec.zkclient.IZkStateListener;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class DefaultZkStateListener implements IZkStateListener {
	public static final Logger log = LoggerFactory
			.getLogger(DefaultZkStateListener.class);

	@Override
	public void handleStateChanged(KeeperState state) throws Exception {
		log.warn("state:"+state);
	}

	@Override
	public void handleNewSession() throws Exception {
		log.warn("handleNewSession");
	}
}

