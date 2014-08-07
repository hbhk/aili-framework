package org.hbhk.aili.core.server.context;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Classname:RequestContext
 * @Description:请求的上下文信息
 * 
 */
public final class RequestContext {
	private static Log log = LogFactory.getLog(RequestContext.class);

	private static ThreadLocal<RequestContext> context = new ThreadLocal<RequestContext>() {
		@Override
		protected RequestContext initialValue() {
			return new RequestContext();
		}
	};
	// 远程调用请求的方法名称和url
	private String remoteReqMethod;

	private String remoteReqURL;
	// 请求的模块名称
	private String moduleName;

	private String requestId;

	private String ip;

	public String getRequestId() {
		return requestId;
	}

	public String getModuleName() {
		return moduleName;
	}

	private RequestContext() {
		this.requestId = UUID.randomUUID().toString();
	}

	public String getRemoteRequestMethod() {
		return this.remoteReqMethod;
	}

	public String getRemoteRequestURL() {
		return this.remoteReqURL;
	}

	public String getIp() {
		return this.ip;
	}

	public static RequestContext getCurrentContext() {
		return context.get();

	}

	public static void setCurrentContext(String remoteReqMethod,
			String remoteReqURL) {
		setCurrentContext(remoteReqMethod, remoteReqURL, null);
	}

	public static void setCurrentContext(String remoteReqMethod,
			String remoteReqURL, String ip) {
		RequestContext requestContext = getCurrentContext();
		requestContext.remoteReqMethod = remoteReqMethod;
		requestContext.remoteReqURL = remoteReqURL;
		requestContext.ip = ip;
	}

	public static void setCurrentContext(String moduleName) {
		RequestContext requestContext = getCurrentContext();
		requestContext.moduleName = moduleName;
	}

	public static void remove() {
		context.remove();
	}
	
	public static HttpServletRequest getRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra)
				.getRequest();
		return request;
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static void setSessionAttribute(String name, Object value) {
		getSession().setAttribute(name, value);
	}

	public static void setSessionAttributes(Map<String, Object> attrs) {
		if (attrs == null || attrs.size() == 0) {
			log.warn("attrs is null");
			return;
		}
		for (Entry<String, Object> entry : attrs.entrySet()) {
			String key = entry.getKey();
			if (StringUtils.isEmpty(key)) {
				log.error("attrs is null");
			}
			Object value = entry.getValue();
			getSession().setAttribute(key, value);
		}
	}

	public static void setRequestAttribute(String name, Object value) {
		getRequest().setAttribute(name, value);
	}

	public static void setRequestAttributes(Map<String, Object> attrs) {
		if (attrs == null || attrs.size() == 0) {
			log.warn("attrs is null");
			return;
		}
		for (Entry<String, Object> entry : attrs.entrySet()) {
			String key = entry.getKey();
			if (StringUtils.isEmpty(key)) {
				log.error("attrs is null");
			}
			Object value = entry.getValue();
			getRequest().setAttribute(key, value);
		}
	}

}
