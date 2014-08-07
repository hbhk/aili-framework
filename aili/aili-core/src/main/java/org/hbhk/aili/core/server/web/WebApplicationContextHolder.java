package org.hbhk.aili.core.server.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

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
