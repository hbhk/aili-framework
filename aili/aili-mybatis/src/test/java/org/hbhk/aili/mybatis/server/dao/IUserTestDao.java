package org.hbhk.aili.mybatis.server.dao;

import java.util.List;
import java.util.Map;


public interface IUserTestDao<T> {

	List<T> get(Map<String, Object> params);
}
