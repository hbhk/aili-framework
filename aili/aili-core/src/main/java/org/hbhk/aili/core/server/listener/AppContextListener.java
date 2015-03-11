package org.hbhk.aili.core.server.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.server.deploy.ModuleManager;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class AppContextListener implements ServletContextListener {

	private final Log log = LogFactory.getLog(getClass());

	private static ServletContext servletContext;

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("appcontext",
				sce.getServletContext().getContextPath());
		log.info("appcontext:" + sce.getServletContext().getContextPath());
		servletContext = sce.getServletContext();
        ModuleManager.export(servletContext);
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

}
