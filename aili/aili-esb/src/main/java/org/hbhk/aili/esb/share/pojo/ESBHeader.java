package org.hbhk.aili.esb.share.pojo;

import org.hbhk.aili.esb.share.ex.ESBIllegalArgumentException;

public class ESBHeader {
	
	/** The version. */
	private String version;
	
	/** The business id. */
	private String businessId;
	
	/** The business desc1. */
	private String businessDesc1;
	
	/** The business desc2. */
	private String businessDesc2;
	
	/** The business desc3. */
	private String businessDesc3;
	
	/** The request id. */
	private String requestId;
	
	/** The response id. */
	private String responseId;
	
	/** The source system. */
	private String sourceSystem;
	
	/** The target system. */
	private String targetSystem;
	
	/** The esb service code. */
	private String esbServiceCode;
	
	/** The back service code. */
	private String backServiceCode;
	
	/** The message format. */
	private String messageFormat;
	
	/** The exchange pattern. */
	private Integer exchangePattern;
	
	/** The sent sequence. */
	private Integer sentSequence;
	
	/** The result code. */
	private Integer resultCode;
	
	/** The authentication. */
	private AuthInfo authentication;
	
	/** The status list. */
	private StatusList statusList = new StatusList();

	/**
	 * 版本号, 编码规则由各应用自行决定.
	 * 
	 * @return value
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 版本号, 编码规则由各应用自行决定.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 用于两端应用跟踪服务，属于同一个businessId的消息即表示在业务含义上相同。.
	 * 
	 * @return value
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * 用于两端应用跟踪服务，属于同一个businessId的消息即表示在业务含义上相同。.
	 * 
	 * @param businessId
	 *            the new business id
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * 业务保留字段1，用于描述业务的辅助信息.
	 * 
	 * @return value
	 */
	public String getBusinessDesc1() {
		return businessDesc1;
	}

	/**
	 * 业务保留字段1，用于描述业务的辅助信息.
	 * 
	 * @param businessDesc1
	 *            the new business desc1
	 */
	public void setBusinessDesc1(String businessDesc1) {
		this.businessDesc1 = businessDesc1;
	}

	/**
	 * 业务保留字段2，用于描述业务的辅助信息.
	 * 
	 * @return value
	 */
	public String getBusinessDesc2() {
		return businessDesc2;
	}

	/**
	 * 业务保留字段2，用于描述业务的辅助信息.
	 * 
	 * @param businessDesc2
	 *            the new business desc2
	 */
	public void setBusinessDesc2(String businessDesc2) {
		this.businessDesc2 = businessDesc2;
	}

	/**
	 * 业务保留字段3，用于描述业务的辅助信息.
	 * 
	 * @return value
	 */
	public String getBusinessDesc3() {
		return businessDesc3;
	}

	/**
	 * 业务保留字段3，用于描述业务的辅助信息.
	 * 
	 * @param businessDesc3
	 *            the new business desc3
	 */
	public void setBusinessDesc3(String businessDesc3) {
		this.businessDesc3 = businessDesc3;
	}

	/**
	 * 用于标识请求消息的唯一性.
	 * 
	 * @return value
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * 用于标识请求消息的唯一性.
	 * 
	 * @param requestId
	 *            the new request id
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * 用于标识响应消息的唯一性.
	 * 
	 * @return value
	 */
	public String getResponseId() {
		return responseId;
	}

	/**
	 * 用于标识响应消息的唯一性.
	 * 
	 * @param responseId
	 *            the new response id
	 */
	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	/**
	 * 记录客户端的前端接入系统标识，ESB为每个接入的系统设定固定的常量，如ERP， CRM， OA。。。.
	 * 
	 * @return value
	 */
	public String getSourceSystem() {
		return sourceSystem;
	}

	/**
	 * 记录客户端的前端接入系统标识，ESB为每个接入的系统设定固定的常量，如ERP， CRM， OA。。。.
	 * 
	 * @param sourceSystem
	 *            the new source system
	 */
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	/**
	 * 记录客户端的后端接入系统标识.
	 * 
	 * @return value
	 */
	public String getTargetSystem() {
		return targetSystem;
	}

	/**
	 * 记录客户端的后端接入系统标识.
	 * 
	 * @param targetSystem
	 *            the new target system
	 */
	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}

	/**
	 * ESB提供给服务消费端的服务编码.
	 * 
	 * @return value
	 */
	public String getEsbServiceCode() {
		return esbServiceCode;
	}

	/**
	 * ESB提供给服务消费端的服务编码.
	 * 
	 * @param esbServiceCode
	 *            the new esb service code
	 */
	public void setEsbServiceCode(String esbServiceCode) {
		this.esbServiceCode = esbServiceCode;
	}

	/**
	 * 服务提供端提供给ESB的服务编码.
	 * 
	 * @return value
	 */
	public String getBackServiceCode() {
		return backServiceCode;
	}

	/**
	 * 服务提供端提供给ESB的服务编码.
	 * 
	 * @param backServiceCode
	 *            the new back service code
	 */
	public void setBackServiceCode(String backServiceCode) {
		this.backServiceCode = backServiceCode;
	}

	/**
	 * 消息格式，如SOAP，XML，JSON，Binary等，这些格式可扩展.
	 * 
	 * @return value
	 */
	public String getMessageFormat() {
		return messageFormat;
	}

	/**
	 * 消息格式，如SOAP，XML，JSON，Binary等，这些格式可扩展.
	 * 
	 * @param messageFormat
	 *            the new message format
	 */
	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	/**
	 * 1-请求/响应，2-请求/回调，3-单向（只有请求没有结果返回）.
	 * 
	 * @return value
	 */
	public Integer getExchangePattern() {
		return exchangePattern;
	}

	/**
	 * 1-请求/响应，2-请求/回调，3-单向（只有请求没有结果返回）.
	 * 
	 * @param exchangePattern
	 *            the new exchange pattern
	 */
	public void setExchangePattern(Integer exchangePattern) {
		this.exchangePattern = exchangePattern;
	}

	/**
	 * 用来标识是否是重发的消息，第一次发送为1，后续每次重发加1.
	 * 
	 * @return value
	 */
	public Integer getSentSequence() {
		return sentSequence;
	}

	/**
	 * 用来标识是否是重发的消息，第一次发送为1，后续每次重发加1.
	 * 
	 * @param sentSequence
	 *            the new sent sequence
	 */
	public void setSentSequence(Integer sentSequence) {
		this.sentSequence = sentSequence;
	}

	/**
	 * 响应结果状态:0-失败，1-成功.
	 * 
	 * @return the result code
	 */
	public Integer getResultCode() {
		return resultCode;
	}

	/**
	 * 响应结果状态:0-失败，1-成功.
	 * 
	 * @param resultCode
	 *            the new result code
	 */
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * 用户认证信息.
	 * 
	 * @return value
	 */
	public AuthInfo getAuthentication() {
		return authentication;
	}

	/**
	 * 用户认证信息.
	 * 
	 * @param authentication
	 *            the new authentication
	 */
	public void setAuthentication(AuthInfo authentication) {
		this.authentication = authentication;
	}

	/**
	 * 状态信息，可用于记录执行状态和对应的时间戳.
	 * 
	 * @return value
	 */
	public StatusList getStatusList() {
		return statusList;
	}

	/**
	 * 状态信息，可用于记录执行状态和对应的时间戳.
	 * 
	 * @param statusList
	 *            the new status list
	 */
	public void setStatusList(StatusList statusList) {
		this.statusList = statusList;
	}

	/**
	 * 消息格式：<状态ID>@<时间戳>:<状态ID>@<时间戳> ...
	 * 
	 * @return the string
	 */
	public String statusListToString() {
		StringBuffer stringBuffer = new StringBuffer();
		for (StatusInfo statusInfo : statusList.getStatusInfoList()) {
			stringBuffer.append(statusInfo.getStatusId())
			.append("@")
			.append(statusInfo.getTimeStamp())
			.append(":");
		}
		if(stringBuffer.length()>1){
			return stringBuffer.substring(0, stringBuffer.lastIndexOf(":"));
		}
		return null;
	}

	/**
	 * 检查必填项有值,否则报错.
	 */
	public void validate() {
		assertNotNull(version);
		assertNotNull(esbServiceCode);
	}

	/**
	 * Assert not null.
	 * 
	 * @param param
	 *            the param
	 * @throws ESBIllegalArgumentException
	 *             the eSB illegal argument exception
	 */
	public void assertNotNull(Object param) throws ESBIllegalArgumentException {
		if (param == null) {
			throw new ESBIllegalArgumentException();
		} else if (param instanceof String && "".equals(param)) {
			throw new ESBIllegalArgumentException();
		}
	}
}