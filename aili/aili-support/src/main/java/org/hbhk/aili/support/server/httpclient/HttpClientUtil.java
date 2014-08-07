package org.hbhk.aili.support.server.httpclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.hbhk.aili.support.server.httpclient.exception.ClientException;

public class HttpClientUtil extends Client {

	public HttpClientUtil(CloseableHttpClient client, String url) {
		super(client);
		this.request = new HttpPost(url);
		this.params = new HashMap<String, String>();
	}
	
	public HttpClientUtil(CloseableHttpClient client, String url,String jsonOrJson) {
		super(client);
		this.request = new HttpPost(url);
		this.params = new String();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addParams() throws ClientException {
		Map<String, String> params = (Map<String, String>) this.params;
		if (params.size() > 0) {
			HttpPost post = (HttpPost) this.request;
			List<NameValuePair> nvps = new ArrayList<NameValuePair>(params.size());
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		}
	}

	@Override
	protected void addParamsXml() throws ClientException {
		String params = (String)this.params;
		StringEntity myEntity = new StringEntity(params, "UTF-8"); 
		HttpPost post = (HttpPost) this.request;
		post.addHeader("Content-Type", "text/xml; charset=UTF-8");  
		post.setEntity(myEntity);
		
	}

	@Override
	protected void addParamsJson() throws ClientException {
		String params = (String)this.params;
		StringEntity myEntity = new StringEntity(params, "UTF-8"); 
		HttpPost post = (HttpPost) this.request;
		post.addHeader("Content-Type", "application/json; charset=UTF-8");  
		post.setEntity(myEntity);
	}

}
