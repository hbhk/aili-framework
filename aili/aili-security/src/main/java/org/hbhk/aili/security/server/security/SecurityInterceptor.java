package org.hbhk.aili.security.server.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.security.server.service.IUserService;
import org.hbhk.aili.security.share.define.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

public class SecurityInterceptor extends HandlerInterceptorAdapter {

	protected Log log = LogFactory.getLog(getClass());
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	@Autowired(required=false)
	private IUserService userService; 

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return false;
		}
		// 用户请求URL
		String url = urlPathHelper.getLookupPathForRequest(request);
		String username = (String) request.getSession().getAttribute(
				UserConstants.CURRENT_USER_NAME);
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		boolean auth = userService.validate(url, username);
		// 是否有权限
		if (auth == true) {
			return true;
		} else {
			return false;
		}

	}

}
