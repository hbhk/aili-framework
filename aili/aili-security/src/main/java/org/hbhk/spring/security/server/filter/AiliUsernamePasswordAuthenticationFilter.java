package org.hbhk.spring.security.server.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AiliUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	public static final Logger log = LoggerFactory
			.getLogger(AiliUsernamePasswordAuthenticationFilter.class);
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		return super.attemptAuthentication(request, response);
	}
}
