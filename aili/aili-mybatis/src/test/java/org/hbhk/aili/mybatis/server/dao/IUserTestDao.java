package org.hbhk.aili.mybatis.server.dao;

import java.util.List;
import java.util.Map;

import org.hbhk.aili.mybatis.server.model.UserInfo;


public interface IUserTestDao<T> {

	List<T> get(Map<String, Object> params);
	
	UserInfo insert(UserInfo user);
	
}
