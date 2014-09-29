package org.hbhk.aili.security.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.hbhk.aili.security.server.dao.ILoginLogInfoDao;
import org.hbhk.aili.security.server.service.ILoginLogInfoService;
import org.hbhk.aili.security.share.pojo.LoginLogInfo;
import org.hbhk.aili.security.share.util.UUIDUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * LoginLogInfoManager
 * 
 * @author 何波
 * 
 */
@Transactional
@Service
public class LoginLogInfoService implements ILoginLogInfoService {

	@Autowired
	private ILoginLogInfoDao loginLogInfoDao;

	public LoginLogInfo save(LoginLogInfo model) {
		model.setCreateTime(new Date());
		model.setId(UUIDUitl.getUuid());
		return loginLogInfoDao.save(model);
	}

	public LoginLogInfo update(LoginLogInfo model) {
		model.setUpdateTime(new Date());
		model.setUpdateUser("admin");
		return loginLogInfoDao.update(model);
	}

	public LoginLogInfo getOne(LoginLogInfo model) {
		return null;
	}

	public List<LoginLogInfo> get(LoginLogInfo model) {
		return null;
	}

	public List<LoginLogInfo> get(LoginLogInfo model, Page page) {
		return null;
	}

	@Override
	public Pagination<LoginLogInfo> queryLogsByPage(Page page, Sort sort,
			Map<String, Object> params) {
		return loginLogInfoDao.queryLogsByPage(page, sort, params);
	}

}
