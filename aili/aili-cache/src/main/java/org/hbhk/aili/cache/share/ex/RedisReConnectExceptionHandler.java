package org.hbhk.aili.cache.share.ex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.cache.server.templet.impl.MemoryCacheTemplet;
import org.hbhk.aili.core.server.web.WebApplicationContextHolder;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisTemplate;


public class RedisReConnectExceptionHandler  implements Thread.UncaughtExceptionHandler {
	private static final Log log = LogFactory.getLog(MemoryCacheTemplet.class);
	private boolean flag = true;
	private static volatile boolean isDeal = false;
	private int sleep =20;
	private ExecutorService  taskExecutor  = Executors.newCachedThreadPool();
    @SuppressWarnings("unchecked")
	@Override
    public void uncaughtException(Thread t, Throwable e) {
    	//只能一个线程处理断开重连添加监听
    	if(isDeal){
    		log.info("正在处理断开重连添加监听...");
    		return;
    	}
    	isDeal = true;
		//处理端口重连并注册监理
		while(flag){
			try {
				RedisTemplate<String,Object> redisTemplate = WebApplicationContextHolder.getApplicationContext().getBean("redisTemplate", RedisTemplate.class);
				redisTemplate.getConnectionFactory().getConnection();
				Set<String> keys = MemoryCacheTemplet.subMap.keySet();
				List<String> newKeys = new ArrayList<String>();
				newKeys.addAll(keys);
				MemoryCacheTemplet.subMap.clear();
				for (final String key : newKeys) {
					 if(!MemoryCacheTemplet.subMap.containsKey(key)){
						 taskExecutor.execute(new Runnable() {
							@Override
							public void run() {
								registerListener(key);
							}
						});
					 }
				}
				log.info("断开重连已经完成");
				flag = false;
				isDeal = false;
			} catch (Exception e2) {
				log.error(e2.getMessage(), e2);
				try {
					TimeUnit.SECONDS.sleep(sleep);
				} catch (InterruptedException e1) {
					log.error(e1);
				}
			}
		}
    }
    
    @SuppressWarnings({ "unchecked"})
	public void registerListener(final String key){
		//添加监听
		 RedisTemplate<String,Object> redisTemplate = WebApplicationContextHolder.getApplicationContext().getBean("redisTemplate", RedisTemplate.class);
		 JedisConnection  redisConnection = (JedisConnection) redisTemplate.getConnectionFactory().getConnection();
		 if (redisConnection.isSubscribed()) {
				throw new IllegalStateException("Retrieved connection is already subscribed; aborting listening");
		}
		redisConnection.subscribe(new MessageListener() {
			@Override
			public void onMessage(Message message, byte[] channels) {
				String key = new String(message.getChannel());
				 MemoryCacheTemplet.subMap.put(key, key);
				if(MemoryCacheTemplet.cache.containsKey(key)){
					log.info("删除本地 缓存key:"+key);
					MemoryCacheTemplet.cache.remove(key);
				}else{
					log.info("本地缓存无需更新key:"+key);
				}
			}
		}, key.getBytes());
	}
}

