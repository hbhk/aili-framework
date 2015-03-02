package org.hbhk.aili.rpc.server.zkclient;
import java.util.List;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class ZKChildListeners implements  InitializingBean {
	public static final Logger log = LoggerFactory.getLogger(ZKChildListeners.class);
	private ZkClient zkClient;
	private List<IChildListener> childListeners;
	private IZkStateListener zkStateListener;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(childListeners != null){
			//添加数据监听
			for (IChildListener listener : childListeners) {
				//获取监听目录
				String path = listener.getPath();
				log.debug(path+"-->"+listener);
				//注册监听
				zkClient.subscribeChildChanges(path, listener);
			}
		}
		if(zkStateListener == null){
			zkStateListener = new DefaultZkStateListener();
		}
		//添加zk断开重连监听
		zkClient.subscribeStateChanges(zkStateListener);
		int num = zkClient.numberOfListeners();
		//总监听数
		log.debug("zk总监听数:"+num);
	}
	public ZkClient getZkClient() {
		return zkClient;
	}
	public void setZkClient(ZkClient zkClient) {
		this.zkClient = zkClient;
	}
	public List<IChildListener> getChildListeners() {
		return childListeners;
	}
	public void setChildListeners(List<IChildListener> childListeners) {
		this.childListeners = childListeners;
	}
	public IZkStateListener getZkStateListener() {
		return zkStateListener;
	}
	public void setZkStateListener(IZkStateListener zkStateListener) {
		this.zkStateListener = zkStateListener;
	}
	
	
	
}

