package org.hbhk.aili.core.server.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
@Component
public class WebApplicationContextHolder implements ApplicationContextAware {
	
	private static volatile ApplicationContext context;
	
	private static final Object lock = new Object();
	
	/**
	 * 注入WebApplicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
	    synchronized (lock) {
	        context = applicationContext;
        }
	}
	
	public static void setContext(ApplicationContext context) {
		WebApplicationContextHolder.context = context;
	}

	public static WebApplicationContext getWebApplicationContext() {
		return (WebApplicationContext)context;
	}
	
	public static ApplicationContext getApplicationContext() {
		return (ApplicationContext)context;
	}

}
