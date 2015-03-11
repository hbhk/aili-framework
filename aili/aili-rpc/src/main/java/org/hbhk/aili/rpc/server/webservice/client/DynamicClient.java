package org.hbhk.aili.rpc.server.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.xfire.client.Client;

/**
 * 
 * @Description: 远程调用增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class DynamicClient {

	public Object[] getWebService(String url, String action, Object[] params)
			throws MalformedURLException, Exception {
		Client client = new Client(new URL(url));
		Object[] results = client.invoke(action, params);
		return results;

	}

}
