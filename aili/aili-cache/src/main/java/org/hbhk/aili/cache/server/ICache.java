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

	// /**
	// * 一次性取出所有内容
	// * @return
	// */
	// Map<K,V> get();
	// /**
	// * 失效一组缓存
	// *
	// * 使旧的一组缓存全部失效
	// * 如果是LRU的在下一次使用会自动加载最新的
	// *
	// * @date 2012-11-12 上午11:13:00
	// * @see
	// */
	// void invalid();
	//
	// /**
	// * 失效key对应的缓存
	// * 如果是LRU的会在下一次使用这个Key时自动加载最新的
	// * 如果是Strong的会Throws RuntimeException异常，不允许失效部分数据
	// * @param key
	// * @see
	// */
	 void invalid(K ... key);
	//
	// /**
	// * 失效传入的多个key对应的缓存
	// * @param keys
	// * @see
	// */
	// void invalidMulti(K ... keys);
}
