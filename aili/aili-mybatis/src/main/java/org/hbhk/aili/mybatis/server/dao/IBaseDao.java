package org.hbhk.aili.mybatis.server.dao;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.hbhk.aili.mybatis.share.model.BaseInfo;

public interface IBaseDao<T extends BaseInfo,PK> {

	@UpdateProvider(type = BaseInfo.class, method = "insert")
	int insert(T t);
	
	@UpdateProvider(type = BaseInfo.class, method = "update")
	int update(T t);
	
	@SelectProvider(type = BaseInfo.class, method = "getById")
	T getById(@Param("id") PK id);

	@DeleteProvider(type = BaseInfo.class, method = "deleteById")
	int deleteById(@Param("id") PK id);

	@UpdateProvider(type = BaseInfo.class, method = "updateStatusById")
	int updateStatusById(@Param("id") PK id, @Param("status") int status);

}
