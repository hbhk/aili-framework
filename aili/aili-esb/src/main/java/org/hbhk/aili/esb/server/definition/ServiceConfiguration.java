package org.hbhk.aili.esb.server.definition;

import org.hbhk.aili.esb.server.process.ICallBackProcess;
import org.hbhk.aili.esb.server.process.IProcess;
import org.hbhk.aili.esb.server.transfer.IMessageTransform;

public class ServiceConfiguration {

	@SuppressWarnings("rawtypes")
	private IMessageTransform reqConvertor;
	
	@SuppressWarnings("rawtypes")
	private IMessageTransform resConvertor;
	
	/** The processor. */
	private IProcess processor;//处理类
	
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
	public IMessageTransform getReqConvertor() {
		return reqConvertor;
	}

	@SuppressWarnings("rawtypes")
	public void setReqConvertor(IMessageTransform reqConvertor) {
		this.reqConvertor = reqConvertor;
	}

	@SuppressWarnings("rawtypes")
	public IMessageTransform getResConvertor() {
		return resConvertor;
	}

	@SuppressWarnings("rawtypes")
	public void setResConvertor(IMessageTransform resConvertor) {
		this.resConvertor = resConvertor;
	}

	public IProcess getProcessor() {
		return processor;
	}

	public void setProcessor(IProcess processor) {
		this.processor = processor;
	}

	public ICallBackProcess getCallBackProcess() {
		return callBackProcess;
	}

	public void setCallBackProcess(ICallBackProcess callBackProcess) {
		this.callBackProcess = callBackProcess;
	}
	
	
}
