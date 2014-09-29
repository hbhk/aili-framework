package org.hbhk.aili.security.server.service;

import java.util.Map;

import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.hbhk.aili.security.share.pojo.LoginLogInfo;



/**
 * LoginLogInfoManager
 * @author  何波
 *
 */
public interface ILoginLogInfoService extends ICommonService<LoginLogInfo> {

	Pagination<LoginLogInfo> queryLogsByPage(Page page, Sort sort,
			 Map<String, Object> params);
	
}
