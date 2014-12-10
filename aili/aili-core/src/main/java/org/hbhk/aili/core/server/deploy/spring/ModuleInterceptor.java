package org.hbhk.aili.core.server.deploy.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hbhk.aili.core.server.context.RequestContext;
import org.hbhk.aili.core.share.util.MyStringUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ModuleInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return;
		}
		HandlerMethod m = (HandlerMethod) handler;
		//获取模块名
		String  clsName =m.getBean().getClass().getName();
		String[] arr = clsName.split("\\.");
		String moduleName = arr[3];
		String contextPath = request.getContextPath() + "/";
		request.setAttribute("base", contextPath);
		if (modelAndView != null
				&& !StringUtils.isEmpty(modelAndView.getViewName())) {
			if (!moduleName.startsWith("/")) {
				moduleName = "/" + moduleName;
			}
			String viewName = modelAndView.getViewName();
			if (filter(viewName)) {
				if (!viewName.startsWith("/")) {
					viewName = "/" + viewName;
				}
				modelAndView.setViewName(moduleName + viewName);

				request.setAttribute("images", contextPath + "images"
						+ moduleName);
				request.setAttribute("scripts", contextPath + "scripts"
						+ moduleName);
				request.setAttribute("styles", contextPath + "styles"
						+ moduleName);
				moduleName = moduleName.substring(moduleName.indexOf("/") + 1,
						moduleName.length());
				RequestContext.setCurrentContext(moduleName);
			}

		}

		super.postHandle(request, response, handler, modelAndView);
	}

	private boolean filter(String viewName) {
		if (viewName.startsWith("redirect:")){
			return false;
		}
		if (viewName.startsWith("http:")){
			return false;
		}
		if (viewName.startsWith("https:")){
			return false;
		}
		if(MyStringUtils.mulStrCount(viewName, "/")>1){
			return false;
		}

		return true;

	}
}
