package org.hbhk.aili.test.server.ws.impl;

import javax.jws.WebService;

import org.hbhk.aili.test.server.ws.IGetInfoService;

@WebService(endpointInterface = "org.hbhk.aili.test.server.ws.IGetInfoService")
public class GetInfoService implements IGetInfoService {

	@Override
	public int add(int num1, int num2) {
		return num1 + num2;
	}

	@Override
	public String getRetInfo(String name, int age) {
		return name + "" + age;
	}

}