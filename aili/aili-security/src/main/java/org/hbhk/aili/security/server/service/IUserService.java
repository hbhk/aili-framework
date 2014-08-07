package org.hbhk.aili.security.server.service;

import org.hbhk.aili.security.share.pojo.UserInfo;

public interface IUserService {
	
	UserInfo getMe(String username);
	UserInfo getUser(UserInfo user);
	boolean login(String username, String password);
	
	UserInfo save(UserInfo user);
	
	boolean validate(String url);
	boolean validate(String username,String url);
	void logout();
}