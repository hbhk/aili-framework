package org.hbhk.aili.mybatis.server.dao;

import java.util.Map;

import org.hbhk.aili.mybatis.server.support.Page;
import org.hbhk.aili.mybatis.server.support.Pagination;
import org.hbhk.aili.mybatis.share.model.UserInfo;


public interface IUserDao extends  IBaseDao<UserInfo, Long> {

	Pagination<UserInfo> getPagination(Map<String, Object> params, Page page);

}
