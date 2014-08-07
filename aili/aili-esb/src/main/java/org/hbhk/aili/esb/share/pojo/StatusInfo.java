package org.hbhk.aili.esb.share.pojo;
public class StatusInfo {
	
	/** The status id. */
	private String statusId;
	
	/** The time stamp. */
	private long timeStamp;
	
	/**
	 * Instantiates a new status info.
	 * 
	 * @param statusId
	 *            the status id
	 * @param timeStamp
	 *            the time stamp
	 */
	public StatusInfo(String statusId, long timeStamp) {
		super();
		this.statusId = statusId;
		this.timeStamp = timeStamp;
	}
	
	// consumer REQUEST
//		/**
//		 * <p>
//		 * 可选
//		 * </p>
//		 * <p>
//		 * 服务无消费端调用接口API的开始（Called）
//		 * </p>
//		 * <p>
//		 * 此状态标识了业务组件与接口组件的分界点
//		 * </p>
//		 * 
//		 */
//		public static final int STATUS_AT_CONSUMER_CALLED = 102;
//		/**
//		 * <p>
//		 * 可选
//		 * </p>
//		 * <p>
//		 * 服务无消费端生成请求消息后（Created）
//		 * </p>
//		 * <p>
//		 * 此状态表示刚刚生成了请求消息
//		 * </p>
//		 */
//		public static final int STATUS_AT_CONSUMER_CREATED = 105;
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * 服务无消费端发送请求消息前（Sent）
//		 * </p>
//		 * <p>
//		 * 此状态表示马上要发送请求消息
//		 * </p>
//		 */
//		public static final int STATUS_AT_CONSUMER_SENT = 108;
//		// ESB REQUEST
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * ESB接收到请求消息后(Received)
//		 * </p>
//		 */
//		public static final int STATUS_AT_ESB_REQ_RECEIVED = 202;
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * ESB进行路由转换前（transforming）
//		 * </p>
//		 */
//		public static final int STATUS_AT_ESB_REQ_TRANSFORMING = 205;
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * ESB路由到目标队列前（Sent）
//		 * </p>
//		 */
//		public static final int STATUS_AT_ESB_REQ_SENT = 208;
//		// provider
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * 服务提供端接收到请求消息后(Received)
//		 * </p>
//		 */
//		public static final int STATUS_AT_PROVIDER_RECEIVED = 302;
//		/**
//		 * <p>
//		 * 可选
//		 * </p>
//		 * <p>
//		 * 服务提供端交由服务处理前（Processing）
//		 * </p>
//		 */
//		public static final int STATUS_AT_PROVIDER_PROCESSING = 305;
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * 服务提供端发送响应消息前（Sent）
//		 * </p>
//		 */
//		public static final int STATUS_AT_PROVIDER_SENT = 308;
//		// ESB RESPONSE
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * ESB接收到响应消息后(Received)
//		 * </p>
//		 */
//		public static final int STATUS_AT_ESB_RESP_RECEIVED = 402;
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * ESB进行路由转换前（transforming）
//		 * </p>
//		 */
//		public static final int STATUS_AT_ESB_RESP_TRANSFORMING = 405;
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * ESB路由到目标队列前（Sent）
//		 * </p>
//		 */
//		public static final int STATUS_AT_ESB_RESP_SENT = 408;
//		// consumer RESPONSE
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * 服务消费端接收到响应消息后(Received)
//		 * </p>
//		 */
//		public static final int STATUS_AT_CONSUMER_RECEIVED = 502;
//		/**
//		 * <p>
//		 * 可选
//		 * </p>
//		 * <p>
//		 * 服务消费端调用回调服务处理前（Processing）
//		 * </p>
//		 */
//		public static final int STATUS_AT_CONSUMER_PROCESSING = 505;
//		/**
//		 * <p>
//		 * 必须
//		 * </p>
//		 * <p>
//		 * 完成处理后（Completed）
//		 * </p>
//		 * <p>
//		 * 标识了整个服务的调用完成
//		 * </p>
//		 */
//		public static final int STATUS_COMPLETE = 999;
//
	/**
	 * 状态码.
	 * 
	 * @return value
	 */
	public String getStatusId() {
		return statusId;
	}

	/**
	 * 状态码.
	 * 
	 * @param statusId
	 *            the new status id
	 */
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	/**
	 * 自从1970-1-1 以来经过的毫秒数.
	 * 
	 * @return value
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * 自从1970-1-1 以来经过的毫秒数.
	 * 
	 * @param timeStamp
	 *            the new time stamp
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}