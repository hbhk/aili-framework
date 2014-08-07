package org.hbhk.aili.security.server.cache;

import org.hbhk.aili.cache.server.templet.impl.RedisCacheTemplet;

public class LoginLimitCache {

	private RedisCacheTemplet<String> storage;
	// 失效时间
	private int exp;
	/**
	 * 1允许重复登陆 2 只允许一次登陆 3 允许重复登陆，把上一次登陆踢出 默认 3
	 */
	private int strategy = 3;

	public void setStorage(RedisCacheTemplet<String> storage) {
		this.storage = storage;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getStrategy() {
		return strategy;
	}

	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}

	public RedisCacheTemplet<String> getStorage() {
		return storage;
	}

	public int getExp() {
		return exp;
	}

	public void set(String key, String value) {
		if (exp == 0) {
			storage.set(key, value);
		} else {
			storage.set(key, value, exp);
		}
	}

	public String get(String key) {
		return storage.get(key);
	}

	public void invalid(String key) {
		storage.invalid(key);
	}

}
