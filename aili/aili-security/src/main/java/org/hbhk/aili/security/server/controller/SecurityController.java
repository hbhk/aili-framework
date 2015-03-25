package org.hbhk.aili.security.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.server.context.RequestContext;
import org.hbhk.aili.core.server.web.BaseController;
import org.hbhk.aili.core.share.entity.ResultEntity;
import org.hbhk.aili.security.server.context.UserContext;
import org.hbhk.aili.security.server.service.ILoginLogInfoService;
import org.hbhk.aili.security.server.service.IUserService;
import org.hbhk.aili.security.share.define.SecurityConstant;
import org.hbhk.aili.security.share.define.UserConstants;
import org.hbhk.aili.security.share.model.LoginLogInfo;
import org.hbhk.aili.security.share.model.UserInfo;
import org.hbhk.aili.support.server.email.IEmailService;
import org.hbhk.aili.support.server.email.impl.EmailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(SecurityConstant.moduleName)
public class SecurityController extends BaseController {

	private static Log log = LogFactory.getLog(SecurityController.class);
	@Autowired
	private IUserService userService;

	@Autowired
	private IEmailService emailService;
	@Autowired
	private ILoginLogInfoService logInfoService;

	@RequestMapping("/login")
	@ResponseBody
	public ResultEntity login(HttpServletResponse response,
			HttpServletRequest request, String userName, String password) {
		try {
			if (userService.login(userName, password)) {
				LoginLogInfo log = new LoginLogInfo();
				log.setUser(userName);
				log.setIp(getIpAddr(request));
				logInfoService.save(log);
				return returnSuccess();
			} else {
				return returnException();
			}
		} catch (Exception e) {
			log.error("login", e);
			return returnException(e.getMessage());
		}

	}

	@RequestMapping("/logout")
	public String logout() {
		try {
			UserContext.remove();
			RequestContext.getSession().setAttribute(
					UserConstants.CURRENT_USER_NAME, null);
			return "redirect:/user/loginpage.htm";
		} catch (Exception e) {
			log.error("logout", e);
			return "redirect:/user/loginpage.htm";
		}

	}

	@RequestMapping("/regist")
	@ResponseBody
	public ResultEntity regist(HttpServletRequest request, UserInfo user,
			String code) {
		try {
			String scode = (String) request.getSession().getAttribute(
					UserConstants.VALIDATECODE_SESSION_KEY);
			if (scode == null) {
				return returnException("验证码不正确");
			}
			if (code != null && !code.equalsIgnoreCase(scode)) {
				return returnException("验证码不正确");
			}
			// 验证用户是否存在
			UserInfo u = new UserInfo();
			u.setUserName(user.getUserName());
			if (userService.getUser(u) != null) {
				return returnException("用户名已经被注册");
			}
			userService.save(user);
			RequestContext.setSessionAttribute(UserConstants.CURRENT_USER_NAME,
					user.getEmail());
			EmailInfo emailInfo = new EmailInfo();
			emailInfo.setSubject("买客网用户注册");
			String info = "恭喜您成功注册！您的用户名为：" + user.getUserName();
			emailInfo.setContext(info);
			List<String> emails = new ArrayList<String>();
			emails.add(user.getEmail());
			emailInfo.setEmails(emails);
			emailService.sendEmail(emailInfo);
			return returnSuccess();
		} catch (Exception e) {
			log.error("regist", e);
			return returnException("注册失败");
		}
	}

	@RequestMapping("/validateEmail")
	@ResponseBody
	public ResultEntity getUserByMail(String mail) {
		try {
			UserInfo u = new UserInfo();
			u.setEmail(mail);
			if (userService.getUser(u) == null) {
				return returnSuccess();
			} else {
				return returnException();
			}
		} catch (Exception e) {
			log.error("getUserByMail", e);
			return returnException(e.getMessage());
		}
	}

	@RequestMapping("/validateName")
	@ResponseBody
	public ResultEntity getUserByName(String name) {
		try {
			UserInfo u = new UserInfo();
			u.setNickName(name);
			if (userService.getUser(u) == null) {
				return returnSuccess();
			} else {
				return returnException();
			}
		} catch (Exception e) {
			log.error("getUserByName", e);
			return returnException(e.getMessage());
		}
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		try {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Cdn-Src-Ip");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			log.error("getIpAddr", e);
		}
		return ip;
	}
}