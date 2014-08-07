package org.hbhk.aili.cache.server.templet;

import java.util.Map;

public interface ICacheTemplet<K, V> {

	boolean set(K key, V value);

	boolean set(K key, V value, int expire);

	V get(K key);

	void invalid(K key);

	void invalid(K... keys);

	boolean set(Map<K, V> values);

	boolean set(Map<K, V> values, int expire);

	long ttl(K key);

	boolean isExitKey(K key);

}
