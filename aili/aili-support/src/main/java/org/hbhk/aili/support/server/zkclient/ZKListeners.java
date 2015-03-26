package org.hbhk.aili.support.server.zkclient;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

/**
 * @author 何波
 * @date 2015年3月4日 下午3:36:40 
 */
public class ZKListeners implements  InitializingBean {
	public static final Logger log = LoggerFactory.getLogger(ZKListeners.class);
	private ZkClient zkClient;
	public static List<IDataListener> dataListeners;
	public static List<IChildListener> childListeners;
	private IZkStateListener zkStateListener;
	private ZkSerializer zkSerializer;
	private String basePackages;
	
    private static final String RESOURCE_PATTERN = "/**/*.class";  
	@Override
	public void afterPropertiesSet() throws Exception {
		initScanPackages();
		if(zkSerializer != null){
			zkClient.setZkSerializer(zkSerializer);
		}
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
		if(log.isDebugEnabled()){
			int num = zkClient.numberOfListeners();
			//总监听数
			log.debug("zk监听数:"+num);
		}
	}
	
	private void initScanPackages() throws Exception{
		if(basePackages != null){
			String[] packagesList = basePackages.split(",");
			for (String pkg : packagesList) { 
				ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();  
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +  
                        ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;  
                Resource[] resources = resourcePatternResolver.getResources(pattern);  
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);  
                for (Resource resource : resources) {  
                    if (resource.isReadable()) {  
                        MetadataReader reader = readerFactory.getMetadataReader(resource);  
                        String className = reader.getClassMetadata().getClassName();  
                        Class<?> clz =  Class.forName(className);
                        if(!clz.isInterface()){
                        	Class<?>[] clsList = clz.getInterfaces();
                        	for (int i = 0;clsList != null && i < clsList.length; i++) {
                        		Class<?> cls = clsList[i];
                        		if(cls.isAssignableFrom(IDataListener.class)){
                            		IDataListener dataListener = (IDataListener) clz.newInstance();
                            		if(dataListeners == null){
                            			dataListeners = new ArrayList<IDataListener>();
                            		}
                            		dataListeners.add(dataListener);
                            	}
                            	if(cls.isAssignableFrom(IChildListener.class)){
                            		IChildListener childListener = (IChildListener) clz.newInstance();
                            		if(childListeners == null){
                            			childListeners = new ArrayList<IChildListener>();
                            		}
                            		childListeners.add(childListener);
                            	}
							}
                        	
                        }
                    }  
                }  
            }  
		}
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
		ZKListeners.dataListeners = dataListeners;
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
		ZKListeners.childListeners = childListeners;
	}

	public String getBasePackages() {
		return basePackages;
	}

	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}
	
	
}

