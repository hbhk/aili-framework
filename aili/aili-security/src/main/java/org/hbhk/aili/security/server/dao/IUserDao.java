package org.hbhk.aili.security.server.dao;

import java.util.Map;

import org.hbhk.aili.orm.server.annotation.NativeQuery;
import org.hbhk.aili.orm.server.annotation.QueryParam;
import org.hbhk.aili.orm.server.dao.GenericEntityDao;
import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.hbhk.aili.security.share.pojo.UserInfo;

public interface IUserDao  extends GenericEntityDao<UserInfo, String> {
	
	@NativeQuery(model=UserInfo.class, value="queryUsersByPage")
	Pagination<UserInfo> queryUsersByPage(Page page,Sort sort, @QueryParam Map<String, Object> params);

}