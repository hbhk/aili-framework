package org.hbhk.aili.mybatis.server;

import java.util.List;
import java.util.Map;


public interface IUserDao<T> {

	List<T> get(Map<String, Object> params);
}
