package org.hbhk.aili.cache.server;


/**
 * 缓存接口
 * 
 * @param <K>
 *            缓存Key类型
 * @param <V>
 *            缓存Value类型
 */
public interface ICache<K, V> {

	 /**
	 * 标记当前Cache的UUID
	 * @return
	 */
	String getCacheId();

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            缓存Key
	 * @return 缓存Value
	 */
	V get(K key);

	/**
	 * 存储
	 * 
	 * @param key
	 *            缓存Key
	 * @return 缓存Value
	 */
	void set(K key, V value);
	
	void set(K key, V value,int expire);

	 /**
	 * 失效key对应的缓存
	 * 如果是LRU的会在下一次使用这个Key时自动加载最新的
	 * 如果是Strong的会Throws RuntimeException异常，不允许失效部分数据
	 * @param key
	 * @see
	 */
	 void invalid(K ... key);
}
