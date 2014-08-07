package org.hbhk.aili.security.server.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.security.share.define.UserConstants;

public class HttpSessionEventPublisher implements HttpSessionListener {

	private Log log = LogFactory.getLog(getClass());

	public void sessionCreated(HttpSessionEvent event) {
		
		//event.getSession().setMaxInactiveInterval(20); 

	}
	
	@SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		String username = (String) session
				.getAttribute(UserConstants.USER_INFO_SESSION);
		ServletContext application = session.getServletContext();
		List<String> onlineUserList = (List<String>) application.getAttribute(UserConstants.USER_INFO_COUNT);
		onlineUserList.remove(username);

		log.info(username + "已经退出！");

	}


}
