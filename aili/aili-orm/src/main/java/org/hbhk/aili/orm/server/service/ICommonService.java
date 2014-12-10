package org.hbhk.aili.orm.server.service;

import java.util.List;

import org.hbhk.aili.orm.server.surpport.Page;

public interface ICommonService<T> {

	T save(T model);

	T update(T model);

	T getOne(T model);

	List<T> get(T model);

	List<T> get(T model, Page page);

}
