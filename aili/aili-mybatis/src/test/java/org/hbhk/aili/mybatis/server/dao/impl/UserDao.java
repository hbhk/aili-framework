package org.hbhk.aili.mybatis.server.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.hbhk.aili.mybatis.server.dao.IUserDao;
import org.hbhk.aili.mybatis.share.model.UserInfo;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
public class UserDao extends SqlSessionDaoSupport  {

	@Override
	@Autowired(required = false)
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	public int deleteById(Long id) {
		return 0;
	}
	public int updateStatusById(Long id, int status) {
		return 0;
	}
	public UserInfo getById(Long id) {
		return null;
	}
}
