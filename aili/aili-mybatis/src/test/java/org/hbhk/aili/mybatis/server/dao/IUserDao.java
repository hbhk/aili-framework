package org.hbhk.aili.mybatis.server.dao;

import org.apache.ibatis.annotations.ResultType;
import org.hbhk.aili.mybatis.share.model.UserInfo;

public interface IUserDao<T> {
	
	@ResultType(value =UserInfo.class)
	UserInfo get(Long id);
}

