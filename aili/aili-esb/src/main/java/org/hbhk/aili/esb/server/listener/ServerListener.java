package org.hbhk.aili.esb.server.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.esb.server.definition.Configuration;
import org.hbhk.aili.esb.share.pojo.ESBHeader;
import org.hbhk.aili.esb.share.pojo.ServiceMessage;
import org.hbhk.aili.esb.share.util.HeaderUtils;

/**
 * 消息监听基类
 */
public class ServerListener implements MessageListener {
	private Log log = LogFactory.getLog(getClass()); 
	@Override
	public void onMessage(Message message) {
		ESBHeader header;
		try {
			header = HeaderUtils.fillServiceHeader(message);
			String body = ((TextMessage) message).getText();
			ServiceMessage serviceMessage = new ServiceMessage(header, body);
			Configuration.getServerThreadPool().process(serviceMessage);
		} catch (JMSException e) {
			log.error(e.getMessage(), e);
		}
	}

}
