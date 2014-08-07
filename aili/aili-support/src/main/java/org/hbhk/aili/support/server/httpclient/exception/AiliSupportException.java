package org.hbhk.aili.support.server.httpclient.exception;

/** sdk调用异常基类 */
public class AiliSupportException extends Exception {

	private static final long serialVersionUID = 5639616328972239397L;

	public AiliSupportException() {
		super();
	}

	public AiliSupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public AiliSupportException(String message) {
		super(message);
	}

	public AiliSupportException(Throwable cause) {
		super(cause);
	}
	
	
}
