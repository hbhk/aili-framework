package org.hbhk.aili.jms.share.pojo;

/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class ServiceMessage {
	// 头信息
	/** The header. */
	private JmsHeader header;

	// 消息体
	/** The body. */
	private String body;
	/**
	 * Instantiates a new service message.
	 */
	public ServiceMessage() {
	}

	/**
	 * 创建一个新的实例 ServiceMessage.
	 * 
	 * @param header
	 *            the header
	 * @param body
	 *            the body
	 * @author HuangHua
	 * @date 2012-12-21 上午10:11:57
	 */
	public ServiceMessage(JmsHeader header, String body) {
		super();
		this.header = header;
		this.body = body;
	}

	/**
	 * Gets the header.
	 * 
	 * @return the header
	 */
	public JmsHeader getHeader() {
		return header;
	}

	/**
	 * Sets the header.
	 * 
	 * @param header
	 *            the new header
	 */
	public void setHeader(JmsHeader header) {
		this.header = header;
	}

	/**
	 * Gets the body.
	 * 
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 * 
	 * @param body
	 *            the new body
	 */
	public void setBody(String body) {
		this.body = body;
	}

}
