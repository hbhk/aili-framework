package org.hbhk.aili.mybatis.server.dao;

import org.apache.ibatis.annotations.Param;
import org.hbhk.aili.mybatis.server.model.Order;
import org.hbhk.aili.mybatis.server.model.Person;

public interface IPersonDao extends  IBaseDao<Person, Long> {

	Person selectPersonFetchOrder(@Param("id") Long id);
	
	Order selectOrdersFetchPerson(@Param("id") Long id);
}

