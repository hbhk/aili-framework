package org.hbhk.aili.jms.server.definition;

import org.hbhk.aili.jms.server.process.ICallBackProcess;
import org.hbhk.aili.jms.server.process.IProcess;
import org.hbhk.aili.jms.server.transfer.IMessageConvertor;

public class ServiceConfiguration {

	@SuppressWarnings("rawtypes")
	private IMessageConvertor reqConvertor;
	
	@SuppressWarnings("rawtypes")
	private IMessageConvertor resConvertor;
	
	/** The processor. */
	private IProcess<?> processor;//处理类
	
	/** The call back process. */
	private ICallBackProcess callBackProcess;//回调处理类
	
	private String requestQueue;
	
	private String responseQueue;
	
	private String esbStatusQueue;
	
	public String getEsbStatusQueue() {
		return esbStatusQueue;
	}

	public void setEsbStatusQueue(String esbStatusQueue) {
		this.esbStatusQueue = esbStatusQueue;
	}

	public String getRequestQueue() {
		return requestQueue;
	}

	public void setRequestQueue(String requestQueue) {
		this.requestQueue = requestQueue;
	}

	public String getResponseQueue() {
		return responseQueue;
	}

	public void setResponseQueue(String responseQueue) {
		this.responseQueue = responseQueue;
	}

	@SuppressWarnings("rawtypes")
	public IMessageConvertor getReqConvertor() {
		return reqConvertor;
	}

	@SuppressWarnings("rawtypes")
	public void setReqConvertor(IMessageConvertor reqConvertor) {
		this.reqConvertor = reqConvertor;
	}

	@SuppressWarnings("rawtypes")
	public IMessageConvertor getResConvertor() {
		return resConvertor;
	}

	@SuppressWarnings("rawtypes")
	public void setResConvertor(IMessageConvertor resConvertor) {
		this.resConvertor = resConvertor;
	}

	public IProcess<?> getProcessor() {
		return processor;
	}

	public void setProcessor(IProcess<?> processor) {
		this.processor = processor;
	}

	public ICallBackProcess getCallBackProcess() {
		return callBackProcess;
	}

	public void setCallBackProcess(ICallBackProcess callBackProcess) {
		this.callBackProcess = callBackProcess;
	}
	
	
}
