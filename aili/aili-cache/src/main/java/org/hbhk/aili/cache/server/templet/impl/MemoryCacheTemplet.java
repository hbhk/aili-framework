package org.hbhk.aili.cache.server.templet.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.cache.server.pub.impl.DefaultRedisMessage;
import org.hbhk.aili.cache.server.templet.ICacheTemplet;
import org.hbhk.aili.cache.share.ex.RedisReConnectExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

public class MemoryCacheTemplet<V> implements ICacheTemplet<String, V> {
	private static final Log log = LogFactory.getLog(MemoryCacheTemplet.class);
	public Map<String, V> cache = new ConcurrentHashMap<String, V>(10000);

	// private Timer ttlTimer;
	private ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(5);

	private volatile Map<String, TimerTask> ttlMap;
	
	private DefaultRedisMessage redisMessage;
	
	@Autowired(required =false)
	private  RedisTemplate<String, Object> redisTemplate;
	
	private boolean isCluster = false;
	
	public static Map<String, String> subMap = new ConcurrentHashMap<String, String>();
	
	private ExecutorService  taskExecutor  = Executors.newCachedThreadPool(new ThreadFactory() {
		
		@Override
		public Thread newThread(Runnable r) {
			 System.out.println(this + " creating new Thread");
		        Thread t = new Thread(r);
		        log.error("created " + t + " ID:" + t.getId());
		        t.setUncaughtExceptionHandler(new RedisReConnectExceptionHandler());
		        log.error("eh=" + t.getUncaughtExceptionHandler());
		        return t;
		}
	});
	private  JedisConnection  redisConnection = null;
	public MemoryCacheTemplet() {
		// ttlTimer = new Timer();
		ttlMap = new HashMap<String, TimerTask>();
	}
	public void registerListener(final String key){
		//添加监听
		redisConnection = (JedisConnection) redisTemplate.getConnectionFactory().getConnection();
		if (redisConnection.isSubscribed()) {
			throw new IllegalStateException("Retrieved connection is already subscribed; aborting listening");
		}
		subMap.put(key, key);
		redisConnection.subscribe(new MessageListener() {
			@Override
			public void onMessage(Message message, byte[] channels) {
				String key = new String(message.getChannel());
				if(cache.containsKey(key)){
					log.info("删除本地 缓存key:"+key);
					cache.remove(key);
				}else{
					log.info("本地缓存无需更新key:"+key);
				}
			}
		}, key.getBytes());
	}
	private void sendMessage(final String key){
		if(!isCluster){
			return;
		}
		if(redisMessage == null){
			redisMessage = new DefaultRedisMessage();
			redisMessage.setRedisTemplate(redisTemplate);
		}
		redisMessage.sendMessage(key, key);
		if(subMap.containsKey(key)){
			return;
		}
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				registerListener(key);
			}
			
		});
		
	}

	@Override
	public boolean set(String key, V value) {
		try {
			keyNot(key);
			cache.put(key, value);
			sendMessage(key);
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
			sendMessage(key);
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
		sendMessage(key);
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
			sendMessage(key);
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

	public DefaultRedisMessage getRedisMessage() {
		return redisMessage;
	}
	public void setRedisMessage(DefaultRedisMessage redisMessage) {
		this.redisMessage = redisMessage;
	}
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	public boolean getIsCluster() {
		return isCluster;
	}
	public void setIsCluster(boolean isCluster) {
		this.isCluster = isCluster;
	}

	
}
