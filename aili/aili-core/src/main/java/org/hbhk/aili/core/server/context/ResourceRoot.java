package org.hbhk.aili.core.server.context;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class ResourceRoot implements ServletContextAware {

	private ServletContext context;

	public static final String resourcePrefix ="/resource";


	public void init() {
		this.context.setAttribute(
				"resourceRoot", this.context.getContextPath() + resourcePrefix);
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

}
