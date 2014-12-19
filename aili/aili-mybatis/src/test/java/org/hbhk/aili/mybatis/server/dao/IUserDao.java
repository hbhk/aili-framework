package org.hbhk.aili.mybatis.server.dao;

import org.apache.ibatis.annotations.SelectProvider;
import org.hbhk.aili.mybatis.share.model.BaseInfo;
import org.hbhk.aili.mybatis.share.model.UserInfo;

public interface IUserDao {
	@SelectProvider(type = BaseInfo.class, method = "selectOne")
	UserInfo getById(Long id);

	// UserInfo getById(Long id);
}
