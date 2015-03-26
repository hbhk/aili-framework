package org.hbhk.aili.sso.server.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SsoLoginFilter implements Filter {
	public static final Logger log = LoggerFactory.getLogger(SsoLoginFilter.class);

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request ;
		HttpSession session = httpRequest.getSession();
		AttributePrincipal principal = (AttributePrincipal) httpRequest.getUserPrincipal();
		if(principal!=null){
			String userName = principal.getName();
			if(StringUtils.isNotEmpty(userName)){
				//设置用户信息
				session.setAttribute("userName", userName);
			}
		}
	}

	@Override
	public void destroy() {
		
	}
}

