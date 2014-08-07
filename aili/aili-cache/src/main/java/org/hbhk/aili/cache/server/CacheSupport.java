package org.hbhk.aili.cache.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.cache.server.templet.ICacheTemplet;
import org.hbhk.aili.cache.server.templet.impl.MemoryCacheTemplet;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 * 提供支持缓存类
 * 
 * @since
 * @version
 */
public abstract class CacheSupport<V> extends CacheBase<String, V> {

	private static final Log log = LogFactory.getLog(CacheSupport.class);
	
	private ICacheTemplet<String, V> cacheTemplet;
	private int expire;

	@Override
	public void set(String key, V value) {
		try {
			if (expire > 0) {
				cacheTemplet.set(key, value, expire);
			} else {
				cacheTemplet.set(key, value);
			}
		} catch (DataAccessResourceFailureException e) {
			log.warn(e.getMessage(), e);
		}

	}

	@Override
	public V get(String key) {
		V v = null;
		try {
			log.debug("缓存key:"+key);
			if (!cacheTemplet.isExitKey(key)) {
				v = doSet(key);
				set(key, v);
				return v;
			} else {
				v = cacheTemplet.get(key);
			}
		} catch (DataAccessResourceFailureException e) {
			log.warn(e.getMessage(), e);
		}
		return v;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (cacheTemplet ==null){
			cacheTemplet = new MemoryCacheTemplet<V>();
		}
		CacheManager.getInstance().registerCacheProvider(this);
	}

	@Override
	public void invalid(String... keys) {
		try {
			cacheTemplet.invalid(keys);
		} catch (DataAccessResourceFailureException e) {
			log.warn(e.getMessage(), e);
		}
		
	}

	public void setCacheTemplet(ICacheTemplet<String, V> cacheTemplet) {
		this.cacheTemplet = cacheTemplet;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

}
