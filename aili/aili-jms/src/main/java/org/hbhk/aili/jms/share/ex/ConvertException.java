package org.hbhk.aili.jms.share.ex;

/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class ConvertException extends Exception {

	private static final long serialVersionUID = 1L; 
	
	private String message;
	
	public ConvertException(){
		super();
	}

	public ConvertException(String message){
		super(message);
		this.message = message;
	}
	
	public ConvertException(String message, Throwable cause){
		super(message, cause);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
