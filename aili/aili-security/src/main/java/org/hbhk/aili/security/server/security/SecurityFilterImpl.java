package org.hbhk.aili.security.server.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.server.web.WebApplicationContextHolder;
import org.hbhk.aili.security.server.service.IUserService;
import org.hbhk.aili.security.share.define.UserConstants;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

public class SecurityFilterImpl implements Filter {

	private Log log = LogFactory.getLog(getClass());
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	private IUserService userService;

	private static final String loginurl = "/security/loginpage.ctrl";

	private static Set<String> notSecurityUrl = new HashSet<String>();
	static {
		notSecurityUrl.add(loginurl);
		notSecurityUrl.add("/security/validateCode.ctrl");
		notSecurityUrl.add("/security/login.ctrl");
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		userService = (IUserService) WebApplicationContextHolder
				.getWebApplicationContext().getBean("userService");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		//HttpServletResponse servletResponse = (HttpServletResponse) response;
		// 使用req对象获取RequestDispatcher对象
		RequestDispatcher dispatcher = servletRequest
				.getRequestDispatcher(loginurl);
		// 用户请求URL
		String url = urlPathHelper.getLookupPathForRequest(servletRequest);
		if (notSecurityUrl.contains(url)) {
			chain.doFilter(request, response);
			return;
		}
		String username = (String) servletRequest.getSession().getAttribute(
				UserConstants.CURRENT_USER_NAME);
		if (StringUtils.isEmpty(username)) {
			request.setAttribute("errorMsg", "你还没有登录");
			dispatcher.forward(request, response);
			return;
		}
		boolean auth = userService.validate(url, username);
		// 是否有权限
		if (auth == true) {
			chain.doFilter(request, response);
			return;
		} else {
			dispatcher = servletRequest
					.getRequestDispatcher("/security/error.ctrl");
			request.setAttribute("errorMsg", "请求的URL不存在或没有权限访问!");
			dispatcher.forward(request, response);
			return;
		}

	}

	@Override
	public void destroy() {

	}

	@SuppressWarnings("unchecked")
	public void validateCount(HttpServletRequest request,
			HttpServletResponse response, String username)
			throws ServletException, IOException {
		String loginUser = null;
		if (StringUtils.isEmpty(username)) {
			loginUser = (String) request.getParameter("username");
		} else {
			loginUser = username;
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/security/authorizeError.ctrl");
		if (StringUtils.isEmpty(username)) {
			// 得到application验证用户登录次数
			ServletContext application = request.getSession()
					.getServletContext();
			List<String> countStr = (List<String>) application
					.getAttribute(UserConstants.USER_INFO_COUNT);
			List<String> userCount = new ArrayList<String>();
			if (!CollectionUtils.isEmpty(countStr)
					&& !StringUtils.isEmpty(loginUser)) {
				for (String user : countStr) {
					if (user.equals(loginUser)) {
						log.info("username:" + user);
						userCount.add(user);
					}
				}
			}
			Integer count = (Integer) application
					.getAttribute(UserConstants.USER_LOGIN_COUNT);
			if (userCount.size() > 0 && count != null
					&& userCount.size() >= count) {
				request.setAttribute("errorMsg", "你(" + loginUser
						+ ")已经在别处登录,服务器只允许" + count + "次登录!");
				dispatcher.forward(request, response);
				return;
			}
		}
	}

}
