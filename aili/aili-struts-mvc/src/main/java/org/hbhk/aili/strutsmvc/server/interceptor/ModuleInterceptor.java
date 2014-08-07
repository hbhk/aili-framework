package org.hbhk.aili.strutsmvc.server.interceptor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.hbhk.aili.core.server.context.RequestContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * JSP页面定位拦截器
 */
public class ModuleInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -7506471938571588297L;

	private static final String LOCATION = "location";

	private static final String ROOT = "/";

	public String intercept(ActionInvocation invocation) throws Exception {
		//以包名为模块名
		String moduleName = invocation.getProxy().getConfig().getPackageName();
		//将模块名设置到模块上下文中
		RequestContext.setCurrentContext(moduleName);
		Map<String, ResultConfig> results = invocation.getProxy().getConfig().getResults();
		if (results != null) {
			String pages = "/WEB-INF/views/" + moduleName + "/";
			for (ResultConfig result : results.values()) { // 查找result配置
				if (ServletDispatcherResult.class.getName().equals(result.getClassName())) {
					Map<String, String> params = result.getParams();
					if (params != null) {
						String location = (String) params.get(LOCATION);
						location = (location == null ? "" : location.trim());
						if (!location.startsWith(ROOT)) { // 如果不是从根目录开始的
                            // TODO 新版本不允许put，暂用反射解决
                            params = new HashMap<String, String>(params);
                            params.put(LOCATION, pages + location); // 修改路径
                            Field field = result.getClass().getDeclaredField("params");
                            if (! field.isAccessible()) {
                                field.setAccessible(true);
                            }
                            field.set(result, params);
                        }
					}
				}
			}
		}
		//String contextPath = AppContext.getAppContext().getContextPath();
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		String contextPath = request.getContextPath();
		request.setAttribute("images", contextPath + "/images/" + moduleName);
		request.setAttribute("scripts", contextPath + "/scripts/" + moduleName);
		request.setAttribute("styles", contextPath + "/styles/" + moduleName);
		//设置静态资源服务器地址
    	//request.setAttribute("resources", AppContext.getAppContext().getStaticServerAddress());
		return invocation.invoke();
	}

}
