package org.hbhk.aili.mybatis.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.hbhk.aili.mybatis.server.support.MybatisSqlTemplate;
import org.hbhk.aili.mybatis.share.model.BaseInfo;

public interface IBaseDao<T extends BaseInfo, PK> {

	@InsertProvider(type = MybatisSqlTemplate.class, method = "insert")
	int insert(T t);

	@UpdateProvider(type = MybatisSqlTemplate.class, method = "update")
	int update(T t);

	@SelectProvider(type = MybatisSqlTemplate.class, method = "getById")
	T getById(@Param("id") PK id);
	
	@SelectProvider(type = MybatisSqlTemplate.class, method = "get")
	List<T> get(Map<String, Object> params);
	
	/**
	 * params 必须要包含start size 参数并指定数值,
	 * 如果没有默认 0 10
	 * @param params
	 * @return
	 */
	@SelectProvider(type = MybatisSqlTemplate.class, method = "getPage")
	List<T> getPage(Map<String, Object> params);

	@DeleteProvider(type = MybatisSqlTemplate.class, method = "deleteById")
	int deleteById(@Param("id") PK id);

	@UpdateProvider(type = MybatisSqlTemplate.class, method = "updateStatusById")
	int updateStatusById(@Param("id") PK id, @Param("status") int status);

}
