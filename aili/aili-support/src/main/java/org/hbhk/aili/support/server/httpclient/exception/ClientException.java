package org.hbhk.aili.support.server.httpclient.exception;

/**
 * client调用相关异常，比如HTTPCLIENT连接异常，IO异常
 * @since
 */
public class ClientException extends AiliSupportException {

	private static final long serialVersionUID = 6129016498302381018L;

	public ClientException() {
		super();
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(Throwable cause) {
		super(cause);
	}
	
}
