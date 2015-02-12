package org.hbhk.aili.jms.server.sender;

import org.hbhk.aili.jms.share.pojo.ESBHeader;

public interface IJmsSender {
	void sendJms(String queueName, ESBHeader esbHeader, final String body);
}
