package org.hbhk.aili.rpc.server.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.xfire.client.Client;

public class DynamicClient {

	public Object[] getWebService(String url, String action, Object[] params)
			throws MalformedURLException, Exception {
		Client client = new Client(new URL(url));
		Object[] results = client.invoke(action, params);
		return results;

	}

}
