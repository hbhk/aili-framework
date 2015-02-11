package org.hbhk.aili.jms.server.sender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.hbhk.aili.jms.share.pojo.ESBHeader;
import org.hbhk.aili.jms.share.util.HeaderUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 发送JMS异步消息基类
 */
public class DefaultSender {

	// jms模板
	private JmsTemplate jmsTemplate;
	
	// 队列名称
	private String queueName;

	public DefaultSender() {
	}
	public DefaultSender(String queueName,JmsTemplate jmsTemplate) {
		this.queueName = queueName;
		this.jmsTemplate = jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	/**
	 * 发送JMS异步消息
	 */
	public void sendJms(final ESBHeader esbHeader,final String body){
		// 队列名称为空，抛异常
		if(StringUtils.isEmpty(queueName)){
			throw new NullPointerException("队列名称不能为空");
		}
		// 发送异步消息
		jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage();
				// 头转换
				HeaderUtils.header2JMSProperties(esbHeader, message);
				message.setText(body);
				return message;
			}
		});
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	
	
}
