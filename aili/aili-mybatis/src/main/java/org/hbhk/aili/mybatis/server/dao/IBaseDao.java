package org.hbhk.aili.mybatis.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.StatementType;
import org.hbhk.aili.mybatis.server.support.DynamicSqlTemplate;
import org.hbhk.aili.mybatis.share.model.BaseInfo;
public interface IBaseDao<T extends BaseInfo, PK> {

	@InsertProvider(type = DynamicSqlTemplate.class, method = "insert")
	@SelectKey(statement={"select last_insert_id() as id"},keyProperty="id",before =false,
	resultType=Long.class,statementType = StatementType.STATEMENT)
	int insert(T t);

	@UpdateProvider(type = DynamicSqlTemplate.class, method = "update")
	int update(T t);

	@SelectProvider(type = DynamicSqlTemplate.class, method = "getById")
	T getById(@Param("id") PK id);
	
	@SelectProvider(type = DynamicSqlTemplate.class, method = "get")
	List<T> get(Map<String, Object> params);
	
	/**
	 * 
	* @author 何波
	* @Description: 分页查询对应数据
	* @param params
	* @param pageNum
	* @param pageSize
	* @return   
	* List<T>   
	* @throws
	 */
	@SelectProvider(type = DynamicSqlTemplate.class, method = "getPage")
	List<T> getPage(Map<String, Object> params, @Param("pageNum")int pageNum, @Param("pageSize")int pageSize);
	
	@SelectProvider(type = DynamicSqlTemplate.class, method = "getPageTotalCount")
	int getPageTotalCount(Map<String, Object> params);
	
	@DeleteProvider(type = DynamicSqlTemplate.class, method = "deleteById")
	int deleteById(@Param("id") PK id);

	@UpdateProvider(type = DynamicSqlTemplate.class, method = "updateStatusById")
	int updateStatusById(@Param("id") PK id, @Param("status") int status);


}
