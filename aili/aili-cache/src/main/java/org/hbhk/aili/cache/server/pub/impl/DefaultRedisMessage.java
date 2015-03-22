package org.hbhk.aili.cache.server.pub.impl;

import java.io.Serializable;

import org.hbhk.aili.cache.server.pub.IRedisMessage;
import org.springframework.data.redis.core.RedisTemplate;

public class DefaultRedisMessage implements IRedisMessage {

	private RedisTemplate<String, Object> redisTemplate = null;

	@Override
	public void sendMessage(String channel, Serializable message) {
		redisTemplate.convertAndSend(channel, message);
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
