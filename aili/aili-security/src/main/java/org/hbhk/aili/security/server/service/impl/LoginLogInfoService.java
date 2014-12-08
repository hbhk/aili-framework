package org.hbhk.aili.security.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.hbhk.aili.security.server.dao.ILoginLogInfoDao;
import org.hbhk.aili.security.server.service.ILoginLogInfoService;
import org.hbhk.aili.security.share.pojo.LoginLogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 何波
 */
@Transactional
@Service
public class LoginLogInfoService implements ILoginLogInfoService {

	@Autowired
	private ILoginLogInfoDao loginLogInfoDao;
	private  ExecutorService executor = Executors.newFixedThreadPool(5);
	public LoginLogInfo save(final LoginLogInfo model) {
		model.setCreateTime(new Date());
		executor.execute(new Runnable() {
			@Override
			public void run() {
				loginLogInfoDao.save(model);
			}
		} );
		return model;
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
