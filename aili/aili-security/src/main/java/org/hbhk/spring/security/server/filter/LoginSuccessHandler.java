package org.hbhk.spring.security.server.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hbhk.aili.security.server.service.IUserService;
import org.hbhk.spring.security.share.vo.UserDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private IUserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		//登录成功后初始化用户信息
//		UserDetailsVo userDetails = (UserDetailsVo) authentication
//				.getPrincipal();
//		UserDetailsVo userCommand = userService.getMe(userDetails.getUsername());
		response.sendRedirect("/index");
	}

}
