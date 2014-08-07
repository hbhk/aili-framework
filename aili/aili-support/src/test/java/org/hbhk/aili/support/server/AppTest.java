package org.hbhk.aili.support.server;

import org.hbhk.aili.support.server.httpclient.HttpClientUtil;
import org.hbhk.aili.support.server.httpclient.ResponseContent;
import org.hbhk.aili.support.server.httpclient.exception.ClientException;


public class AppTest{
	public static void main(String[] args) throws ClientException {
		String url ="http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=1rENYOIqG1RIMwnfH5uHS1o9";
		ResponseContent<String>  responseContent=HttpClientUtil.post(url).send();
		String ss = responseContent.getResult();
		System.out.println(ss);
	}
}