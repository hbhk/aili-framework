package org.hbhk.aili.cache.server.templet.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.cache.server.templet.ICacheTemplet;

public class MemoryCacheTemplet<V> implements ICacheTemplet<String, V> {
	private static final Log log = LogFactory.getLog(MemoryCacheTemplet.class);
	private Map<String, V> cache = new ConcurrentHashMap<String, V>(10000);

	// private Timer ttlTimer;
	private ScheduledExecutorService scheduledExecutor = Executors
			.newScheduledThreadPool(5);

	private volatile Map<String, TimerTask> ttlMap;

	public MemoryCacheTemplet() {
		// ttlTimer = new Timer();
		ttlMap = new HashMap<String, TimerTask>();
	}

	@Override
	public boolean set(String key, V value) {
		try {
			keyNot(key);
			cache.put(key, value);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}

	}

	private void  keyNot(String key){
		if (StringUtils.isEmpty(key)) {
			throw new RuntimeException("key不允许为null或空串!");
		}
	}
	
	@Override
	public boolean set(String key, V value, int expire) {
		try {
			TimerTask task = ttlMap.get(key);
			if (task != null) {
				task.cancel();
				task = null;
			}
			task = new TTlTimerTask(key);
			// ttlTimer.schedule(task, expire * 1000);
			cache.put(key, value);
			scheduledExecutor.schedule(task, expire, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}

	}

	@Override
	public V get(String key) {
		return cache.get(key);
	}

	@Override
	public void invalid(String key) {
		keyNot(key);
		cache.remove(key);

	}

	@Override
	public boolean set(Map<String, V> values) {
		return false;
	}

	@Override
	public boolean set(Map<String, V> values, int expire) {
		return false;
	}

	@Override
	public long ttl(String key) {
		TimerTask task = ttlMap.get(key);
		if(task==null){
			return 0 ;
		}
		return task.scheduledExecutionTime();
	}

	@Override
	public boolean isExitKey(String key) {
		if (StringUtils.isEmpty(key)) {
			throw new RuntimeException("key不允许为null或空串!");
		}
		return cache.containsKey(key);
	}

	@Override
	public void invalid(String... keys) {
		if (keys == null || keys.length == 0) {
			throw new RuntimeException("key不允许为null或空串!");
		}
		for (String key : keys) {
			cache.remove(key);
		}

	}

	class TTlTimerTask extends TimerTask {

		private String key;

		public TTlTimerTask(String key) {
			super();
			this.key = key;
		}

		@Override
		public void run() {
			log.debug("[" + this.key + "]已经失效");
			invalid(this.key);
		}

	}

}
