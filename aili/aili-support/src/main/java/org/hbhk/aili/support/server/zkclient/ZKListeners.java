package org.hbhk.aili.support.server.zkclient;
import java.util.List;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class ZKListeners implements  InitializingBean {
	public static final Logger log = LoggerFactory.getLogger(ZKListeners.class);
	private ZkClient zkClient;
	private List<IDataListener> dataListeners;
	private List<IChildListener> childListeners;
	private IZkStateListener zkStateListener;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(dataListeners != null){
			//添加数据监听
			for (IDataListener listener : dataListeners) {
				//获取监听目录
				String path = listener.getPath();
				if(path == null || path.equals("")){
					throw new NullPointerException("监听路径path为空");
				}
				log.debug(path+"-->"+listener);
				//注册监听
				zkClient.subscribeDataChanges(path, listener);
			}
		}
		if(childListeners != null){
			//添加数据监听
			for (IChildListener listener : childListeners) {
				//获取监听目录
				String path = listener.getPath();
				if(path == null || path.equals("")){
					throw new NullPointerException("监听路径path为空");
				}
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
		log.debug("zk监听数:"+num);
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
	public IZkStateListener getZkStateListener() {
		return zkStateListener;
	}
	public void setZkStateListener(IZkStateListener zkStateListener) {
		this.zkStateListener = zkStateListener;
	}
	public List<IChildListener> getChildListeners() {
		return childListeners;
	}
	public void setChildListeners(List<IChildListener> childListeners) {
		this.childListeners = childListeners;
	}
	
	
}
