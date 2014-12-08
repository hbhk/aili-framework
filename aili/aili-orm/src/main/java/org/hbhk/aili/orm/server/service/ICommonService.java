package org.hbhk.aili.orm.server.service;

import java.util.List;
import java.util.Map;

import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;

public interface ICommonService<T> {

	T save(T model);

	T update(T model);

	T getOne(T model);

	List<T> get(T model);

	List<T> get(T model, Page page);

	Pagination<T> getPagination(Page page,Sort[] sorts, Map<String, Object> params);

	List<T> getList(Map<String, Object> params);

}
