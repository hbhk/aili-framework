package org.hbhk.aili.esb.share.util;

import java.util.HashMap;
import java.util.Map;

public class ServiceHeader {

	/** The Constant VERSION. */
	public static final String VERSION = "version"; // 版本号 同步+异步
													// 用于服务的版本控制，有可能存在不同的版本。
	/**
	 * The Constant BUSINESS_ID.
	 */
	public static final String BUSINESS_ID = "businessId"; // 业务唯一ID 同步+异步
	// 用于两端应用跟踪服务，属于同一个businessId的消息即表示在业务含义上相同。
	/**
	 * The Constant BUSINESS_DESC1.
	 */
	public static final String BUSINESS_DESC1 = "businessDesc1"; // 业务辅助字段 同步+异步
	// 业务保留字段1，用于描述业务的辅助信息
	/**
	 * The Constant BUSINESS_DESC2 .
	 */
	public static final String BUSINESS_DESC2 = "businessDesc2"; // 业务辅助字段 同步+异步
	// 业务保留字段2，用于描述业务的辅助信息
	/**
	 * The Constant BUSINESS_DESC3 .
	 */
	public static final String BUSINESS_DESC3 = "businessDesc3"; // 业务辅助字段 同步+异步
	// 业务保留字段3，用于描述业务的辅助信息
	/**
	 * The Constant REQUEST_ID .
	 */
	public static final String REQUEST_ID = "requestId"; // 请求唯一ID 同步+异步
	// 用于请求消息的唯一性标识
	/**
	 * The Constant RESPONSE_ID.
	 */
	public static final String RESPONSE_ID = "responseId"; // 响应唯一ID 同步+异步
	// 用于响应消息的唯一性标识
	/**
	 * The Constant SOURCE_SYSTEM.
	 */
	public static final String SOURCE_SYSTEM = "sourceSystem"; // 客户端接入系统 同步+异步
	// 记录客户端的前端接入系统标识，ESB为每个接入的系统设定固定的常量，如ERP，
	// CRM， OA。。。
	/**
	 * The Constant TARGET_SYSTEM .
	 */
	public static final String TARGET_SYSTEM = "targetSystem"; // 请求的目标系统 同步+异步
	// 记录客户端的后端接入系统标识
	/**
	 * The Constant ESB_SERVICE_CODE .
	 */
	public static final String ESB_SERVICE_CODE = "esbServiceCode"; // ESB服务编码
	// 同步+异步
	// ESB提供给服务消费端的服务编码
	/**
	 * The Constant BACK_SERVICE_CODE .
	 */
	public static final String BACK_SERVICE_CODE = "backServiceCode"; // 后端服务编码
	// 同步+异步
	// 服务提供端提供给ESB的服务编码
	/**
	 * The Constant MESSAGE_FORMAT .
	 */
	public static final String MESSAGE_FORMAT = "messageFormat"; // 消息格式 同步+异步
	// 如XML，JSON，二进制对象等
	/**
	 * The Constant EXCHANGE_PATTERN .
	 */
	public static final String EXCHANGE_PATTERN = "exchangePattern"; // 交互模式 异步
	// 请求/响应，请求/回调，单向（只有请求没有结果返回）
	/**
	 * The Constant SENT_SEQUENCE .
	 */
	public static final String SENT_SEQUENCE = "sentSequence"; // 发送计数 异步
	// 用来标识是否是重发的消息，第一次发送为1，后续每次重发加1
	/**
	 * The Constant RESULT_CODE.
	 */
	public static final String RESULT_CODE = "resultCode"; // 响应结果状态 同步+异步
	// 处理结果，成功：SUCCESS，
	// 失败：FAILURE
	/**
	 * The Constant USERNAME.
	 */
	public static final String USERNAME = "username"; // 用户名 同步+异步 用于认证和授权

	/** The Constant PASSWORD. */
	public static final String PASSWORD = "password"; // 密码

	/** The Constant HOSTIP. */
	public static final String HOSTIP = "hostIP"; // 主机IP

	/** The Constant SUCCESS. */
	public static final int SUCCESS = 1;// 头信息中返回的resultCode值，成功--1

	/** The Constant FAILURE. */
	public static final int FAILURE = 0;// 头信息中返回的resultCode值，失败--0

	// consumer REQUEST
	/**
	 * <p>
	 * 可选
	 * </p>
	 * <p>
	 * 服务无消费端调用接口API的开始（Called）
	 * </p>
	 * <p>
	 * 此状态标识了业务组件与接口组件的分界点
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_CONSUMER_CALLED = "ST_102";

	/**
	 * <p>
	 * 可选
	 * </p>
	 * <p>
	 * 服务无消费端生成请求消息后（Created）
	 * </p>
	 * <p>
	 * 此状态表示刚刚生成了请求消息
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_CONSUMER_CREATED = "ST_105";

	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * 服务无消费端发送请求消息前（Sent）
	 * </p>
	 * <p>
	 * 此状态表示马上要发送请求消息
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_CONSUMER_SENT = "ST_108";
	// ESB REQUEST
	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * ESB接收到请求消息后(Received)
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_ESB_REQ_RECEIVED = "ST_202";

	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * ESB进行路由转换前（transforming）
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_ESB_REQ_TRANSFORMING = "ST_205";

	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * ESB路由到目标队列前（Sent）
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_ESB_REQ_SENT = "ST_208";
	// provider
	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * 服务提供端接收到请求消息后(Received)
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_PROVIDER_RECEIVED = "ST_302";

	/**
	 * <p>
	 * 可选
	 * </p>
	 * <p>
	 * 服务提供端交由服务处理前（Processing）
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_PROVIDER_PROCESSING = "ST_305";

	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * 服务提供端发送响应消息前（Sent）
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_PROVIDER_SENT = "ST_308";
	// ESB RESPONSE
	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * ESB接收到响应消息后(Received)
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_ESB_RESP_RECEIVED = "ST_402";

	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * ESB进行路由转换前（transforming）
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_ESB_RESP_TRANSFORMING = "ST_405";

	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * ESB路由到目标队列前（Sent）
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_ESB_RESP_SENT = "ST_408";
	// consumer RESPONSE
	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * 服务消费端接收到响应消息后(Received)
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_CONSUMER_RECEIVED = "ST_502";

	/**
	 * <p>
	 * 可选
	 * </p>
	 * <p>
	 * 服务消费端调用回调服务处理前（Processing）
	 * </p>
	 * .
	 */
	public static final String STATUS_AT_CONSUMER_PROCESSING = "ST_505";

	/**
	 * <p>
	 * 必须
	 * </p>
	 * <p>
	 * 完成处理后（Completed）
	 * </p>
	 * <p>
	 * 标识了整个服务的调用完成
	 * </p>
	 * .
	 */
	public static final String STATUS_COMPLETE = "ST_999";

	/** The version. */
	private String version; // 版本号 同步+异步 用于服务的版本控制，有可能存在不同的版本。

	/** The business id. */
	private String businessId; // 业务唯一ID.同步+异步.用于两端应用跟踪服务，属于同一个businessId的消息即表示在业务含义上相同。

	/** The business desc1. */
	private String businessDesc1; // 业务辅助字段 同步+异步 业务保留字段1，用于描述业务的辅助信息

	/** The business desc2. */
	private String businessDesc2; // 业务辅助字段 同步+异步 业务保留字段2，用于描述业务的辅助信息

	/** The business desc3. */
	private String businessDesc3; // 业务辅助字段 同步+异步 业务保留字段3，用于描述业务的辅助信息

	/** The request id. */
	private String requestId; // 请求唯一ID 同步+异步 用于请求消息的唯一性标识

	/** The response id. */
	private String responseId; // 响应唯一ID 同步+异步 用于响应消息的唯一性标识

	/** The source system. */
	private String sourceSystem; // 客户端接入系统.同步+异步.记录客户端的前端接入系统标识，ESB为每个接入的系统设定固定的常量，如ERP，CRM，OA。。。

	/** The target system. */
	private String targetSystem; // 请求的目标系统 同步+异步 记录客户端的后端接入系统标识

	/** The esb service code. */
	private String esbServiceCode; // ESB服务编码 同步+异步 ESB提供给服务消费端的服务编码

	/** The back service code. */
	private String backServiceCode; // 后端服务编码 同步+异步 服务提供端提供给ESB的服务编码

	/** The message format. */
	private String messageFormat; // 消息格式 同步+异步 如XML，JSON，二进制对象等

	/** The exchange pattern. */
	private String exchangePattern; // 交互模式 异步 请求/响应，请求/回调，单向（只有请求没有结果返回）

	/** The sent sequence. */
	private String sentSequence; // 发送计数 异步 用来标识是否是重发的消息，第一次发送为1，后续每次重发加1

	/** The result code. */
	private String resultCode; // 响应结果状态 同步+异步 处理结果，成功：SUCCESS， 失败：FAILURE

	/** The user name. */
	private String userName; // 用户名 同步+异步 用于认证和授权

	/** The password. */
	private String password; // 密码

	/** The status map. */
	private Map<Integer, Long> statusMap = new HashMap<Integer, Long>(); // 服务状态列表.异步.用于记录服务从请求到相应的整个周期中每个状态的时间戳,具体参见服务状态管理

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 必填.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the business id.
	 * 
	 * @return the business id
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * 选填，建议填写，方便跟踪和处理.
	 * 
	 * @param businessId
	 *            the new business id
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * Gets the business desc1.
	 * 
	 * @return the business desc1
	 */
	public String getBusinessDesc1() {
		return businessDesc1;
	}

	/**
	 * 选填.
	 * 
	 * @param businessDesc1
	 *            the new business desc1
	 */
	public void setBusinessDesc1(String businessDesc1) {
		this.businessDesc1 = businessDesc1;
	}

	/**
	 * Gets the business desc2.
	 * 
	 * @return the business desc2
	 */
	public String getBusinessDesc2() {
		return businessDesc2;
	}

	/**
	 * 选填.
	 * 
	 * @param businessDesc2
	 *            the new business desc2
	 */
	public void setBusinessDesc2(String businessDesc2) {
		this.businessDesc2 = businessDesc2;
	}

	/**
	 * Gets the business desc3.
	 * 
	 * @return the business desc3
	 */
	public String getBusinessDesc3() {
		return businessDesc3;
	}

	/**
	 * 选填.
	 * 
	 * @param businessDesc3
	 *            the new business desc3
	 */
	public void setBusinessDesc3(String businessDesc3) {
		this.businessDesc3 = businessDesc3;
	}

	/**
	 * Gets the request id.
	 * 
	 * @return the request id
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * 不填，由扩展端自动生成.
	 * 
	 * @param requestId
	 *            the new request id
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * Gets the response id.
	 * 
	 * @return the response id
	 */
	public String getResponseId() {
		return responseId;
	}

	/**
	 * 不填，有后端返回。.
	 * 
	 * @param responseId
	 *            the new response id
	 */
	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	/**
	 * Gets the source system.
	 * 
	 * @return the source system
	 */
	public String getSourceSystem() {
		return sourceSystem;
	}

	/**
	 * 不填，由扩展端自动生成.
	 * 
	 * @param sourceSystem
	 *            the new source system
	 */
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	/**
	 * Gets the target system.
	 * 
	 * @return the target system
	 */
	public String getTargetSystem() {
		return targetSystem;
	}

	/**
	 * 不填.
	 * 
	 * @param targetSystem
	 *            the new target system
	 */
	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}

	/**
	 * Gets the back service code.
	 * 
	 * @return the back service code
	 */
	public String getBackServiceCode() {
		return backServiceCode;
	}

	/**
	 * 不填.
	 * 
	 * @param backServiceCode
	 *            the new back service code
	 */
	public void setBackServiceCode(String backServiceCode) {
		this.backServiceCode = backServiceCode;
	}

	/**
	 * Gets the message format.
	 * 
	 * @return the message format
	 */
	public String getMessageFormat() {
		return messageFormat;
	}

	/**
	 * 不填，由扩展端完成.
	 * 
	 * @param messageFormat
	 *            the new message format
	 */
	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	/**
	 * Gets the exchange pattern.
	 * 
	 * @return the exchange pattern
	 */
	public String getExchangePattern() {
		return exchangePattern;
	}

	/**
	 * 不填.
	 * 
	 * @param exchangePattern
	 *            the new exchange pattern
	 */
	public void setExchangePattern(String exchangePattern) {
		this.exchangePattern = exchangePattern;
	}

	/**
	 * Gets the sent sequence.
	 * 
	 * @return the sent sequence
	 */
	public String getSentSequence() {
		return sentSequence;
	}

	/**
	 * 不填.
	 * 
	 * @param sentSequence
	 *            the new sent sequence
	 */
	public void setSentSequence(String sentSequence) {
		this.sentSequence = sentSequence;
	}

	/**
	 * Gets the result code.
	 * 
	 * @return the result code
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * 不填，由后端返回.
	 * 
	 * @param resultCode
	 *            the new result code
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 不填.
	 * 
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 不填.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the status map.
	 * 
	 * @return the status map
	 */
	public Map<Integer, Long> getStatusMap() {
		return statusMap;
	}

	/**
	 * 不填.
	 * 
	 * @param statusMap
	 *            the status map
	 */
	public void setStatusMap(Map<Integer, Long> statusMap) {
		this.statusMap = statusMap;
	}

	/**
	 * Gets the esb service code.
	 * 
	 * @return the esb service code
	 */
	public String getEsbServiceCode() {
		return esbServiceCode;
	}

	/**
	 * 必填.
	 * 
	 * @param esbServiceCode
	 *            the new esb service code
	 */
	public void setEsbServiceCode(String esbServiceCode) {
		this.esbServiceCode = esbServiceCode;
	}

}