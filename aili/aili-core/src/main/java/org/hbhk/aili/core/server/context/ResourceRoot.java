package org.hbhk.aili.core.server.context;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

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
