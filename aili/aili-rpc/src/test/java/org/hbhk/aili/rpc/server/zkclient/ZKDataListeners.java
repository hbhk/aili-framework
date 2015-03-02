package org.hbhk.aili.rpc.server.zkclient;
import java.util.List;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class ZKDataListeners implements  InitializingBean {
	public static final Logger log = LoggerFactory.getLogger(ZKDataListeners.class);
	private ZkClient zkClient;
	private List<IDataListener> dataListeners;
	private IZkStateListener zkStateListener;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//添加数据监听
		for (IDataListener listener : dataListeners) {
			String path = listener.getPath();
			zkClient.subscribeDataChanges(path, listener);
		}
		if(zkStateListener == null){
			zkStateListener = new DefaultZkStateListener();
		}
		//添加zk断开监听
		zkClient.subscribeStateChanges(zkStateListener);
	}
	public ZkClient getZkClient() {
		return zkClient;
	}
	public void setZkClient(ZkClient zkClient) {
		this.zkClient = zkClient;
	}
	public List<IDataListener> getDataListeners() {
		return dataListeners;
	}
	public void setDataListeners(List<IDataListener> dataListeners) {
		this.dataListeners = dataListeners;
	}
	
	
}

