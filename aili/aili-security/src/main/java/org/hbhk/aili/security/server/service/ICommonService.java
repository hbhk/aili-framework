package org.hbhk.aili.security.server.service;

import java.util.List;

public interface ICommonService<T> {

	T save(T model);

	int update(T model);

	T getOne(T model);

	List<T> get(T model);


}
