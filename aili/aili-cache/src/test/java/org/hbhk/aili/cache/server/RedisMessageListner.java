package org.hbhk.aili.cache.server;

import org.hbhk.aili.cache.server.sub.IRedisMessageListener;
import org.springframework.data.redis.connection.Message;

public class RedisMessageListner implements IRedisMessageListener{

	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.println(new String(message.getBody()));
	}

}
