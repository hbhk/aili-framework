package org.hbhk.aili.security.server.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.server.context.RequestContext;
import org.hbhk.aili.core.server.web.WebApplicationContextHolder;
import org.hbhk.aili.core.share.ex.BusinessException;
import org.hbhk.aili.security.server.cache.LoginLimitCache;
import org.hbhk.aili.security.server.context.LoginLimitContext;
import org.hbhk.aili.security.server.context.UserContext;

public class LoginLimitFilter implements Filter {

	protected Log log = LogFactory.getLog(this.getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		String url = RequestContext.getCurrentContext().getRemoteRequestURL();
		if(url.equals("/security/logout.ctrl")){
			Cookie cookieres = new Cookie("loginLimit", null);
			servletResponse.addCookie(cookieres);
			chain.doFilter(request, response);
			return;
		}
		if (checkUrl(url)) {
			chain.doFilter(request, response);
			return;
		}
		// 只处理登陆是不对的，如果是允许重复登陆，且把上一次踢掉
		String doLogin = request.getParameter("doLogin");
		boolean isDoLogin = false;
		String currentTimeMillis = null;
		if (StringUtils.isNotEmpty(doLogin)) {
			isDoLogin = Boolean.parseBoolean(doLogin);
			if (isDoLogin) {
				currentTimeMillis = String.valueOf(System.currentTimeMillis());
				LoginLimitContext.setSessionId(currentTimeMillis);
			}
		}
		// 获取登陆用户
		String loginName = request.getParameter("loginName");
		String userName = UserContext.getCurrentContext().getCurrentUserName();
		if (StringUtils.isEmpty(loginName) && StringUtils.isNotEmpty(userName)) {
			loginName = userName;
		}
		// 从缓存中获取csessionID
		LoginLimitCache cache = WebApplicationContextHolder
				.getWebApplicationContext().getBean("loginLimitCache",
						LoginLimitCache.class);
		int strategy = cache.getStrategy();
		// 1不做处理
		if (strategy == 1) {
			chain.doFilter(request, response);
			return;
		}
		String cloginLimit = null;
		try {
			cloginLimit = cache.get(loginName);
		} catch (Exception e) {
			chain.doFilter(request, response);
			return;
		}
		

		// 用户从未登陆 跳过
		if (StringUtils.isEmpty(loginName) && StringUtils.isEmpty(cloginLimit)) {
			chain.doFilter(request, response);
			return;
		}

		Cookie[] cookies = servletRequest.getCookies();
		if (cookies == null || cookies.length == 0) {
			chain.doFilter(request, response);
			return;
		}
		// 获取用户登陆的时间戳
		String loginLimit = null;
		boolean isjsessionID = true;
		for (int i = 0; i < cookies.length && isjsessionID; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("loginLimit")) {
				loginLimit = cookie.getValue();
				isjsessionID = false;
			}
		}
		if(StringUtils.isEmpty(loginLimit)){
			chain.doFilter(request, response);
			return;
		}
		// 2 只允许一次登陆
		if (userName == null && strategy == 2
				&& StringUtils.isNotEmpty(cloginLimit)) {
			throw new BusinessException("4001", "只允许一次登陆,请退出之前登陆 ");
		}
		// 3 允许重复登陆，把上一次登陆踢出
		if (strategy == 3 && StringUtils.isNotEmpty(cloginLimit)
				&& StringUtils.isNotEmpty(loginLimit)) {
			if (!loginLimit.equals(cloginLimit)) {
				throw new BusinessException("4002", "您已经在别处登陆,请重新登陆");
			}
		}
		if (isDoLogin) {
			cloginLimit = currentTimeMillis;
		}
		Cookie cookieres = new Cookie("loginLimit", cloginLimit);
		servletResponse.addCookie(cookieres);
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {

	}

	private boolean checkUrl(String url) {
		Map<String, String> urlNotsecurity = new HashMap<String, String>();
		urlNotsecurity.put("/security/loginpage.ctrl",
				"/security/loginpage.ctrl");
		urlNotsecurity.put("/security/logout.ctrl", "/security/logout.ctrl");
		urlNotsecurity.put("/security/error.ctrl", "/security/error.ctrl");
		urlNotsecurity.put("/security/validateCode.ctrl",
				"/security/validateCode.ctrl");
		if (urlNotsecurity.containsKey(url)) {
			return true;
		}
		return false;
	}

}
