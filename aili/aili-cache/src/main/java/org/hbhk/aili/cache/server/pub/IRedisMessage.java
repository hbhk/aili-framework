package org.hbhk.aili.cache.server.pub;

import java.io.Serializable;

public interface IRedisMessage {

	void sendMessage(String channel, Serializable message);

}
