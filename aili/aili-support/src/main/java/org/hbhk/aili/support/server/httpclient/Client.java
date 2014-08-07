package org.hbhk.aili.support.server.httpclient;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hbhk.aili.support.server.httpclient.exception.ClientException;
import org.hbhk.aili.support.server.httpclient.exception.ExceptionEntity;
import org.hbhk.aili.support.server.httpclient.exception.ResponseException;
import org.hbhk.aili.support.server.json.JsonUtil;

public abstract class Client {

	protected Log log = LogFactory.getLog(getClass());

	protected Object params;

	protected CloseableHttpClient client;

	protected HttpUriRequest request;

	protected Client(CloseableHttpClient client) {
		this.client = client;
	}

	public static Client post(String url) {
		return new HttpClientUtil(HttpClients.createDefault(), url);
	}
	public static Client postXml(String url) {
		return new HttpClientUtil(HttpClients.createDefault(), url);
	}
	public static Client postJson(String url) {
		return new HttpClientUtil(HttpClients.createDefault(), url);
	}
	public static Client get(String url) {
		return new HttpClientUtil(HttpClients.createDefault(), url);
	}

	@SuppressWarnings("unchecked")
	public Client param(String name, String value) {
		((Map<String, String>) this.params).put(name, value);
		return this;
	}
	public Client param(String xmlOrJson) {
		 this.params = xmlOrJson;
		return this;
	}

	@SuppressWarnings("unchecked")
	public Client param(Object paramMap) {
		if (paramMap != null) {
			if(paramMap instanceof Map){
				((Map<String, String>) this.params).putAll((Map<String,String>) paramMap);
			}
			if(paramMap instanceof String){
				 this.params = paramMap;
			}
		}
		return this;
	}

	protected abstract void addParams() throws ClientException;
	protected abstract void addParamsXml() throws ClientException;
	protected abstract void addParamsJson() throws ClientException;

	public ResponseContent<String> send() throws ClientException {
		ResponseContent<String> result = null;
		try {
			// params
			this.addParams();
			// Create a custom response handler
			ResponseHandler<ResponseContent<String>> responseHandler = new ResponseHandler<ResponseContent<String>>() {
				public ResponseContent<String> handleResponse(
						final HttpResponse response)
						throws ClientProtocolException, IOException {
					ResponseContent<String> result = new ResponseContent<String>();
					result.setStatus(response.getStatusLine().getStatusCode());
					result.setResult(EntityUtils.toString(response.getEntity(),
							Consts.UTF_8));
					return result;
				}
			};

			if (log.isDebugEnabled()) {
				if (this.request != null) {
					log.debug("url:" + this.request.getURI());
				}
			}
			result = this.client.execute(request, responseHandler);
		} catch (Exception e) {
			throw new ClientException(e.getMessage(), e);
		} finally {
			closeClient(this.client);
		}

		return result;
	}
	
	public ResponseContent<String> sendXml() throws ClientException {
		ResponseContent<String> result = null;
		try {
			// params
			this.addParamsXml();
			// Create a custom response handler
			ResponseHandler<ResponseContent<String>> responseHandler = new ResponseHandler<ResponseContent<String>>() {
				public ResponseContent<String> handleResponse(
						final HttpResponse response)
						throws ClientProtocolException, IOException {
					ResponseContent<String> result = new ResponseContent<String>();
					result.setStatus(response.getStatusLine().getStatusCode());
					result.setResult(EntityUtils.toString(response.getEntity(),
							Consts.UTF_8));
					return result;
				}
			};

			if (log.isDebugEnabled()) {
				if (this.request != null) {
					log.debug("url:" + this.request.getURI());
				}
			}
			result = this.client.execute(request, responseHandler);
		} catch (Exception e) {
			throw new ClientException(e.getMessage(), e);
		} finally {
			closeClient(this.client);
		}

		return result;
	}
	
	public ResponseContent<String> sendJson() throws ClientException {
		ResponseContent<String> result = null;
		try {
			// params
			this.addParamsJson();
			// Create a custom response handler
			ResponseHandler<ResponseContent<String>> responseHandler = new ResponseHandler<ResponseContent<String>>() {
				public ResponseContent<String> handleResponse(
						final HttpResponse response)
						throws ClientProtocolException, IOException {
					ResponseContent<String> result = new ResponseContent<String>();
					result.setStatus(response.getStatusLine().getStatusCode());
					result.setResult(EntityUtils.toString(response.getEntity(),
							Consts.UTF_8));
					return result;
				}
			};

			if (log.isDebugEnabled()) {
				if (this.request != null) {
					log.debug("url:" + this.request.getURI());
				}
			}
			result = this.client.execute(request, responseHandler);
		} catch (Exception e) {
			throw new ClientException(e.getMessage(), e);
		} finally {
			closeClient(this.client);
		}

		return result;
	}


	public static void closeClient(CloseableHttpClient client)
			throws ClientException {
		try {
			client.close();
		} catch (IOException e) {
			throw new ClientException(e.getMessage(), e);
		}
	}

	public static void assertNotFoundException(int status, String url)
			throws ResponseException {
		if (status == HttpStatus.SC_NOT_FOUND) {
			throw new ResponseException("not found : " + url,
					HttpStatus.SC_NOT_FOUND);
		}
	}
	
	/**
	 * 发送请求，并把返回的JSON字符串转换为对象
	 * @param parametrized 如果为null，则ResponseContent的result为null
	 * @param parameterClasses
	 * @return
	 * @throws ResponseException
	 * @throws ClientException
	 * @since
	 */
	@SuppressWarnings("unchecked")
	public <T> ResponseContent<T> returnJson(Class<?> parametrized, Class<?>... parameterClasses) throws ResponseException, ClientException {
		ResponseContent<String> content = this.send();
		
		assertNotFoundException(content.getStatus(), this.request.getURI().toString());
		
		if (content.getStatus() != HttpStatus.SC_OK) {
			ExceptionEntity entity = JsonUtil.parseJson(content.getResult(), ExceptionEntity.class);
			throw new ResponseException(content.getStatus(), entity);
		}
		
		ResponseContent<T> result = new ResponseContent<T>();
		result.setStatus(HttpStatus.SC_OK);
		if (parametrized != null) {
			result.setResult((T)JsonUtil.parseJson(content.getResult(), parametrized, parameterClasses));
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public <T> ResponseContent<T> returnJson(Class<?> parametrized) throws ResponseException, ClientException {
		ResponseContent<String> content = this.send();
		
		assertNotFoundException(content.getStatus(), this.request.getURI().toString());
		
		if (content.getStatus() != HttpStatus.SC_OK) {
			ExceptionEntity entity = JsonUtil.parseJson(content.getResult(), ExceptionEntity.class);
			throw new ResponseException(content.getStatus(), entity);
		}
		
		ResponseContent<T> result = new ResponseContent<T>();
		result.setStatus(HttpStatus.SC_OK);
		if (parametrized != null) {
			result.setResult((T)JsonUtil.parseJson(content.getResult(), parametrized));
		}
		return result;
	}


}
