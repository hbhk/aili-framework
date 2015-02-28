package org.hbhk.aili.jms.server.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.jms.server.definition.Configuration;
import org.hbhk.aili.jms.share.pojo.JmsHeader;
import org.hbhk.aili.jms.share.pojo.ServiceMessage;
import org.hbhk.aili.jms.share.util.HeaderUtils;
import org.springframework.stereotype.Component;

/**
 * 消息监听基类
 */
@Component
public class BusinessListener implements MessageListener {
	private Log log = LogFactory.getLog(getClass());

	@Override
	public void onMessage(Message message) {
		try {
			JmsHeader header = HeaderUtils.fillServiceHeader(message);
			String body = ((TextMessage) message).getText();
			ServiceMessage serviceMessage = new ServiceMessage(header, body);
			Configuration.getServerThreadPool().process(serviceMessage);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
