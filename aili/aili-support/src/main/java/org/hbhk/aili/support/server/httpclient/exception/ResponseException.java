package org.hbhk.aili.support.server.httpclient.exception;


/**
 * server端返回的异常，比如验证异常等等
 */
public class ResponseException extends AiliSupportException {

	private static final long serialVersionUID = 7518007863863904991L;

	private int status;
	
	private ExceptionEntity entity;

	public ResponseException(String message, int status) {
		super(message);
		this.status = status;
	}

	public ResponseException(int status, ExceptionEntity entity) {
		super();
		this.status = status;
		this.entity = entity;
	}

	public ResponseException(int status, ExceptionEntity entity, Throwable cause) {
		super(cause);
		this.status = status;
		this.entity = entity;
	}

	public ResponseException(int status, ExceptionEntity entity, String message) {
		super(message);
		this.status = status;
		this.entity = entity;
	}

	public ResponseException(int status, ExceptionEntity entity, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
		this.entity = entity;
	}

	/** HTTP响应码 */
	public int getStatus() {
		return status;
	}

	/** 响应明细 */
	public ExceptionEntity getEntity() {
		return entity;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setEntity(ExceptionEntity entity) {
		this.entity = entity;
	}

	@Override
	public String getMessage() {
		String msg = super.getMessage();
		if (this.entity != null) {
			msg = (msg == null ? this.entity.toString() : msg + ", " + this.entity.toString());
		}
		
		return msg;
	}
	
	
}
