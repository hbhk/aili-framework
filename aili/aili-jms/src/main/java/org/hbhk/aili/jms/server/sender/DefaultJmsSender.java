package org.hbhk.aili.jms.server.sender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.hbhk.aili.jms.share.pojo.JmsHeader;
import org.hbhk.aili.jms.share.util.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * 发送JMS异步消息基类
 */
@Component("jmsSender")
public class DefaultJmsSender implements IJmsSender {

	// jms模板
	@Autowired(required = false)
	private JmsTemplate jmsTemplate;

	public DefaultJmsSender() {
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * 发送JMS异步消息
	 */
	public void sendJms(String queueName, final JmsHeader esbHeader,
			final String body) {
		// 队列名称为空，抛异常
		if (StringUtils.isEmpty(queueName)) {
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

}
