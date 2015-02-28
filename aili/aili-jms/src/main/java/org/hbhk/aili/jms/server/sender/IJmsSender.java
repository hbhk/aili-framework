package org.hbhk.aili.jms.server.sender;

import org.hbhk.aili.jms.share.pojo.JmsHeader;

public interface IJmsSender {
	void sendJms(String queueName, JmsHeader esbHeader, final String body);
}
