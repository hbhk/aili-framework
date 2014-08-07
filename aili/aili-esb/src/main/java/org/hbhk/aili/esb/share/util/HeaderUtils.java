package org.hbhk.aili.esb.share.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.hbhk.aili.esb.share.pojo.ESBHeader;
import org.hbhk.aili.esb.share.pojo.StatusInfo;

public class HeaderUtils {

	/**
	 * 把JMSPropertyProperty中与ESB相关的属性封装到ESBHeader中.
	 * 
	 * @param message
	 *            the message
	 * @return the eSB header
	 * @throws JMSException
	 *             the jMS exception
	 */
	public static ESBHeader fillServiceHeader(Message message) throws JMSException {
		ESBHeader header = new ESBHeader();
		header.setBackServiceCode(message.getStringProperty(ServiceHeader.BACK_SERVICE_CODE));
		header.setBusinessDesc1(message.getStringProperty(ServiceHeader.BUSINESS_DESC1));
		header.setBusinessDesc2(message.getStringProperty(ServiceHeader.BUSINESS_DESC2));
		header.setBusinessDesc3(message.getStringProperty(ServiceHeader.BUSINESS_DESC3));
		header.setBusinessId(message.getStringProperty(ServiceHeader.BUSINESS_ID));
		header.setEsbServiceCode(message.getStringProperty(ServiceHeader.ESB_SERVICE_CODE));
		header.setExchangePattern(message.getIntProperty(ServiceHeader.EXCHANGE_PATTERN));
		header.setMessageFormat(message.getStringProperty(ServiceHeader.MESSAGE_FORMAT));
		header.setRequestId(message.getStringProperty(ServiceHeader.REQUEST_ID));
		header.setResponseId(message.getStringProperty(ServiceHeader.RESPONSE_ID));
		if (message.propertyExists(ServiceHeader.RESULT_CODE)) {
			header.setResultCode(message.getIntProperty(ServiceHeader.RESULT_CODE));
		}
		header.setSourceSystem(message.getStringProperty(ServiceHeader.SOURCE_SYSTEM));
		header.setTargetSystem(message.getStringProperty(ServiceHeader.TARGET_SYSTEM));
		header.setVersion(message.getStringProperty(ServiceHeader.VERSION));

		return header;
	}

	/**
	 * 把header里面的属性填充到JMSProperty中.
	 * 
	 * @param esbHeader
	 *            the esb header
	 * @param outMessage
	 *            the out message
	 * @throws JMSException
	 *             the jMS exception
	 */
	public static void header2JMSProperties(ESBHeader esbHeader, TextMessage outMessage) throws JMSException {
		outMessage.setStringProperty(ServiceHeader.VERSION, esbHeader.getVersion());
		outMessage.setStringProperty(ServiceHeader.BUSINESS_ID, esbHeader.getBusinessId());
		outMessage.setStringProperty(ServiceHeader.BUSINESS_DESC1, esbHeader.getBusinessDesc1());
		outMessage.setStringProperty(ServiceHeader.BUSINESS_DESC2, esbHeader.getBusinessDesc2());
		outMessage.setStringProperty(ServiceHeader.BUSINESS_DESC3, esbHeader.getBusinessDesc3());
		outMessage.setStringProperty(ServiceHeader.REQUEST_ID, esbHeader.getRequestId());
		outMessage.setStringProperty(ServiceHeader.SOURCE_SYSTEM, esbHeader.getSourceSystem());
		if (esbHeader.getResponseId() != null) {
			outMessage.setStringProperty(ServiceHeader.RESPONSE_ID, esbHeader.getResponseId());
		}
		if (esbHeader.getResultCode() != null) {
			outMessage.setIntProperty(ServiceHeader.RESULT_CODE, esbHeader.getResultCode());
		}
		if (esbHeader.getBackServiceCode() != null) {
			outMessage.setStringProperty(ServiceHeader.BACK_SERVICE_CODE, esbHeader.getBackServiceCode());
		}
		if (esbHeader.getTargetSystem() != null) {
			outMessage.setStringProperty(ServiceHeader.TARGET_SYSTEM, esbHeader.getTargetSystem());
		}
		outMessage.setStringProperty(ServiceHeader.ESB_SERVICE_CODE, esbHeader.getEsbServiceCode());
		outMessage.setStringProperty(ServiceHeader.MESSAGE_FORMAT, esbHeader.getMessageFormat());
		outMessage.setIntProperty(ServiceHeader.EXCHANGE_PATTERN, esbHeader.getExchangePattern());
		if (esbHeader.getAuthentication() != null) {
			outMessage.setStringProperty(ServiceHeader.USERNAME, esbHeader.getAuthentication().getUsername());
			outMessage.setStringProperty(ServiceHeader.PASSWORD, esbHeader.getAuthentication().getPassword());
		}
		// 状态信息，根据ITA要求，把所有状态ID前加"ST_"
		for (StatusInfo statusInfo : esbHeader.getStatusList().getStatusInfoList()) {
			outMessage.setLongProperty(statusInfo.getStatusId(), statusInfo.getTimeStamp());
		}
	}

}
