package org.hbhk.aili.security.server.service;

import java.util.Map;

import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.hbhk.aili.security.share.pojo.UserInfo;

public interface IUserService {
	
	UserInfo getMe(String username);
	UserInfo getUser(UserInfo user);
	boolean login(String username, String password);
	
	UserInfo save(UserInfo user);
	
	UserInfo update(UserInfo user);
	
	boolean validate(String url);
	boolean validate(String username,String url);
	void logout();
	Pagination<UserInfo> queryUsersByPage(Page page,Sort sort, Map<String, Object> params);
}