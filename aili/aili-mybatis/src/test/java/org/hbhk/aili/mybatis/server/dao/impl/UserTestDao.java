package org.hbhk.aili.mybatis.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.hbhk.aili.mybatis.server.dao.IUserTestDao;
import org.hbhk.aili.mybatis.server.model.UserInfo;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class UserTestDao extends SqlSessionDaoSupport implements IUserTestDao<UserInfo>  {

	@Override
	@Autowired(required = false)
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	@Override
	public List<UserInfo> get(Map<String, Object> params) {
		return getSqlSession().selectList("org.hbhk.aili.mybatis.server.dao.IUserDao.get");
	}
	@Override
	public UserInfo insert(UserInfo userInfo) {
		getSqlSession().insert("org.hbhk.aili.mybatis.server.dao.IUserDao.insertUser",userInfo);
		return userInfo;
	}
}
