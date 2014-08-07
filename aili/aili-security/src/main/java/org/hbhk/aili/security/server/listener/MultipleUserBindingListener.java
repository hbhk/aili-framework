package org.hbhk.aili.security.server.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.security.share.define.UserConstants;

public class MultipleUserBindingListener implements HttpSessionBindingListener {

	private Log log = LogFactory.getLog(getClass());
	private String username;
    private  int count;
	 
	public MultipleUserBindingListener(String  username,int count) {
		this.username=username;
		this.count = count;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		// 把用户名放入在线列表
		List<String> onlineUserList = (List<String>) application
				.getAttribute(UserConstants.USER_INFO_COUNT);
		// 第一次使用前，需要初始化
		if (onlineUserList == null) {
			onlineUserList = new ArrayList<String>();
			application.setAttribute(UserConstants.USER_INFO_COUNT, onlineUserList);
			application.setAttribute(UserConstants.USER_LOGIN_COUNT, count);
		}
		onlineUserList.add(this.username);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		// 从在线列表中删除用户名
		List<String> onlineUserList = (List<String>) application
				.getAttribute(UserConstants.USER_INFO_COUNT);
		onlineUserList.remove(this.username);
		log.info(this.username + "退出。");

	}

}
