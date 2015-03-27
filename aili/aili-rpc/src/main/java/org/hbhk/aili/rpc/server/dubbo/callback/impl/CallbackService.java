package org.hbhk.aili.rpc.server.dubbo.callback.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hbhk.aili.rpc.server.dubbo.callback.ICallbackListener;
import org.hbhk.aili.rpc.server.dubbo.callback.ICallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallbackService implements ICallbackService {
	private static final Logger log = LoggerFactory
			.getLogger(CallbackService.class);
	private final Map<String, ICallbackListener> listeners = new ConcurrentHashMap<String, ICallbackListener>();

	public CallbackService() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						for (Map.Entry<String, ICallbackListener> entry : listeners.entrySet()) {
							try {
								entry.getValue().changed(getChanged(entry.getKey()));
							} catch (Throwable t) {
								listeners.remove(entry.getKey());
								log.error(t.getMessage(),t);
							}
						}
						// 定时触发变更通知
						Thread.sleep(3000); 
					} catch (Throwable t) { 
						// 防御容错
						log.error(t.getMessage(),t);
					}
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}

	public void addListener(String key, ICallbackListener listener) {
		log.debug("变更通知:" + key);
		listeners.put(key, listener);
		// 发送变更通知
		listener.changed(getChanged(key));
	}

	private String getChanged(String key) {
		return "Changed: "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

}
