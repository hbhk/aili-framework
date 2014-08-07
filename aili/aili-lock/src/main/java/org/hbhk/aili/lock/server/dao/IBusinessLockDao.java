package org.hbhk.aili.lock.server.dao;

import java.util.List;
import java.util.Map;

import org.hbhk.aili.lock.share.pojo.MutexElement;

/**
 * 业务锁数据访问接口
 */
public interface IBusinessLockDao {

	/**
	 * <p>设置业务锁记录，如果不存在记录，插入记录，如果存在记录不做操作，并设置锁的过期时间</p> 
	 */
	Long setnx(String key, String value, int ttl);

	/**
	 * <p>通过业务锁的key得到业务锁描述</p> 
	 */
	String get(String key);

	/**
	 * <p>通过业务锁的key删除业务锁记录</p> 
	 */
	void del(String key);

	/**
	 * <p>通过业务锁的key，批量删除业务锁记录</p> 
	 */
	void delBatch(List<String> keys);

	/**
	 * <p>批量设置业务锁数据</p> 
	 */
	List<Long> setnxBatch(Map<String, MutexElement> map);

	/**
	 * <p>删除ttl时间到了的数据</p> 
	 */
	void delTimeoutData();

	
}
