package org.hbhk.aili.support.server.httpclient;

/**
 * 响应信息，包括HTTP响应码，结果数据，以及异常
 * @author 李光辉
 * @date 2014年4月4日 上午9:12:47
 * @since
 */
public class ResponseContent<T> {

	private int status;
	
	private T result;

	public ResponseContent() { }

	public ResponseContent(int status) {
		this.status = status;
	}

	public ResponseContent(int status, T result) {
		this.status = status;
		this.result = result;
	}

	/** HTTP响应码 */
	public int getStatus() {
		return status;
	}

	/** 结果数据 */
	public T getResult() {
		return result;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
