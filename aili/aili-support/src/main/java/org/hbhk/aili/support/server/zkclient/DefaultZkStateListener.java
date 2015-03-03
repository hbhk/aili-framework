package org.hbhk.aili.support.server.zkclient;
import org.I0Itec.zkclient.IZkStateListener;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

