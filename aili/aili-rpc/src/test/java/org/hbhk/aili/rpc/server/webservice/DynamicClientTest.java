package org.hbhk.aili.rpc.server.webservice;

import java.net.MalformedURLException;

import org.hbhk.aili.rpc.server.webservice.client.DynamicClient;

public class DynamicClientTest {

	
	public static void main(String[] args) throws MalformedURLException,
			Exception {

		DynamicClient web = new DynamicClient();

		Object[] results = web
				.getWebService(
						"http://localhost/remote/url?wsdl",
						"sayHello", new Object[] { "上海"});

		System.out.println(results[0]);

	}

}
