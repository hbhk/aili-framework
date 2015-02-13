package org.hbhk.aili.mybatis.server.dao;

import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;
import org.hbhk.aili.mybatis.server.support.DynamicSqlTemplate;
import org.hbhk.aili.mybatis.server.support.Page;
import org.hbhk.aili.mybatis.server.support.Pagination;
import org.hbhk.aili.mybatis.share.model.UserInfo;


public interface IUserDao extends  IBaseDao<UserInfo, Long> {

	/**
	 * 
	* @author 何波
	* @Description: 注解方式,不需要编写对应mapper，且只能是本dao单表
	* @param params
	* @param page
	* @return   
	* Pagination<UserInfo>   
	* @throws
	 */
	@SelectProvider(type = DynamicSqlTemplate.class, method = "getPagination")
	Pagination<UserInfo> getPagination(Map<String, Object> params, Page page);
	/**
	 * 
	* @author 何波
	* @Description: 编写对应mapper映射文件
	* @param params
	* @param page
	* @return   
	* Pagination<UserInfo>   
	* @throws
	 */
	Pagination<UserInfo> getPagination1(Map<String, Object> params, Page page);

	
}
