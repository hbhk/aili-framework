package org.hbhk.aili.jms.server.definition;

import java.util.HashMap;
import java.util.Map;

import org.hbhk.aili.jms.server.process.ServerThreadPool;


/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class Configuration {

	private static Map<String, ServiceConfiguration> serviceConfigMap = new HashMap<String, ServiceConfiguration>();
	
	private static ServerThreadPool serverThreadPool;

	public static Map<String, ServiceConfiguration> getServiceConfigMap() {
		return serviceConfigMap;
	}

	public void setServiceConfigMap(
			Map<String, ServiceConfiguration> serviceConfigMap) {
		Configuration.serviceConfigMap = serviceConfigMap;
	}

	public static ServerThreadPool getServerThreadPool() {
		return serverThreadPool;
	}

	public void setServerThreadPool(ServerThreadPool serverThreadPool) {
		Configuration.serverThreadPool = serverThreadPool;
	}
	
	
}
