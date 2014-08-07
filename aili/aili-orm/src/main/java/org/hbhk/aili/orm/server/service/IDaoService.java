package org.hbhk.aili.orm.server.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;

public interface IDaoService {
	<T> T getByPrimaryKey(Class<T> clazz, Serializable pk);

	<T> List<T> get(T model,RowMapper<T> rowMapper);
	<T> List<T> get(T model,Page page ,RowMapper<T> rowMapper );
	<T> T getOne(T model ,RowMapper<T> rowMapper);
	<T> T save(T model);
	<T> T update(T model);

	<T> void delete(T model);

	<T> void deleteByPrimaryKey(Class<T> clazz, Serializable pk);

	/**
	 * Similiar with findOneByQuery, the difference is that the params can
	 * contain other definition do not used by query
	 * 
	 * @param <T>
	 * @param queryString
	 * @param params
	 * @return
	 */
	<T> T findOneByQueryEx(String queryString, Map<String, Object> params,
			Sort[] sorts);

	// <T> List<T> findByNamedQuery(String queryName, Map<String,Object>
	// params);
	// <T> List<T> findByNamedQuery(String queryName, Map<String,Object> params,
	// int start, int pageSize);
	// <T> Pagination<T> findByNamedQuery(String queryName, Map<String,Object>
	// params, int start, int pageSize, boolean withGroupby);
	// <T> List<T> findByNamedQuery(String queryName, Map<String,Object> params,
	// Sort[] sorts);
	// <T> List<T> findByNamedQuery(String queryName, Map<String,Object> params,
	// Sort[] sorts, int start, int pageSize);
	// <T> Pagination<T> findByNamedQuery(String queryName, Map<String,Object>
	// params, Sort[] sorts, int start, int pageSize, boolean withGroupby);

	// <T> List<T> findByQuery(String queryString, Map<String,Object> params);
	// <T> List<T> findByQuery(String queryString, Map<String,Object> params,
	// int start, int pageSize);
	// <T> Pagination<T> findByQuery(String queryString, Map<String,Object>
	// params, int start, int pageSize, boolean withGroupby);
	// <T> List<T> findByQuery(String queryString, Map<String,Object> params,
	// Sort[] sorts);
	// <T> List<T> findByQuery(String queryString, Map<String,Object> params,
	// Sort[] sorts, int start, int pageSize);
	// <T> Pagination<T> findByQuery(String queryString, Map<String,Object>
	// params, Sort[] sorts, int start, int pageSize, boolean withGroupby);
	/**
	 * Similiar with findByQuery, the difference is that the params can contain
	 * other definition do not used by query
	 * 
	 * @param <T>
	 * @param queryString
	 * @param params
	 * @param sorts
	 * @param start
	 * @param pageSize
	 * @return
	 */
	<T> List<T> findByQueryEx(String queryString, Map<String, Object> params,
			Sort[] sorts, int start, int pageSize);

	<T> Pagination<T> findByQueryEx(String queryString,
			Map<String, Object> params, Sort[] sorts, int start, int pageSize,
			boolean withGroupby);

	// int batchUpdateByNamedQuery(String queryName, Map<String,Object> params);
	// int batchUpdateByQuery(String queryString, Map<String,Object> params);

	<T> List<T> findByNativeQuery(String queryString, Object[] params,
			RowMapper<T> rowMapper);

	<T> List<T> findByNativeQuery(String queryString, Object[] params,
			int start, int pageSize, RowMapper<T> rowMapper);

	<T> Pagination<T> findByNativeQuery(String queryString, Object[] params,
			int start, int pageSize, boolean withGroupby, RowMapper<T> rowMapper);

	<T> List<T> findByNativeQuery(String queryString, Object[] params,
			Sort[] sorts, RowMapper<T> rowMapper);

	<T> List<T> findByNativeQuery(String queryString, Object[] params,
			Sort[] sorts, int start, int pageSize, RowMapper<T> rowMapper);

	<T> Pagination<T> findByNativeQuery(String queryString, Object[] params,
			Sort[] sorts, int start, int pageSize, boolean withGroupby,
			RowMapper<T> rowMapper);

	<T> T findOneByNativeQuery(String queryString, Object[] params,
			RowMapper<T> rowMapper, Sort[] sorts);

	int batchUpdateByNativeQuery(String queryString, Object[] params,
			Class<?>[] types);

	void executeDDL(String queryString);

	Map<String, Object> executeSP(String spName);

	Map<String, Object> executeSp(String spName, SqlParameter[] sqlParameters,
			Map<String, Object> params);

	void flush();

	<T> void evict(T model);
}
