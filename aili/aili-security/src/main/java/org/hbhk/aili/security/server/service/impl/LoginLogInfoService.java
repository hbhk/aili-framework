package org.hbhk.aili.security.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hbhk.aili.mybatis.server.support.Page;
import org.hbhk.aili.security.server.dao.ILoginLogInfoDao;
import org.hbhk.aili.security.server.service.ILoginLogInfoService;
import org.hbhk.aili.security.share.model.LoginLogInfo;
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
				loginLogInfoDao.insert(model);
			}
		} );
		return model;
	}

	public int update(LoginLogInfo model) {
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


}
