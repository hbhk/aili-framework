package org.hbhk.aili.mybatis.server.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.hbhk.aili.mybatis.server.dao.IUserDao;
import org.hbhk.aili.mybatis.share.model.UserInfo;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class UserDao extends SqlSessionDaoSupport implements IUserDao {

	@Override
	@Autowired(required = false)
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	@Override
	public UserInfo get(Long id) {
		UserInfo user = getSqlSession().selectOne("test.getUser");
		return user;
	}
}
