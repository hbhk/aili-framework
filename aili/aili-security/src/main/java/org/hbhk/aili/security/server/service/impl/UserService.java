package org.hbhk.aili.security.server.service.impl;

import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.cache.server.CacheManager;
import org.hbhk.aili.cache.server.ICache;
import org.hbhk.aili.core.server.context.RequestContext;
import org.hbhk.aili.core.share.ex.BusinessException;
import org.hbhk.aili.core.share.util.EncryptUtil;
import org.hbhk.aili.security.server.cache.LoginLimitCache;
import org.hbhk.aili.security.server.cache.UserResourceCache;
import org.hbhk.aili.security.server.context.LoginLimitContext;
import org.hbhk.aili.security.server.context.UserContext;
import org.hbhk.aili.security.server.dao.IUserDao;
import org.hbhk.aili.security.server.service.IUserService;
import org.hbhk.aili.security.share.define.UserConstants;
import org.hbhk.aili.security.share.pojo.UserInfo;
import org.hbhk.aili.security.share.util.UUIDUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Resource
	private IUserDao userDao;

	private String defaultHead = "images/security/default_head.png";

	@Autowired(required = false)
	private LoginLimitCache limitCache;

	@Override
	public UserInfo getMe(String username) {
		UserInfo u = new UserInfo();
		u.setUsername(username);
		return userDao.getOne(u);
	}

	@Override
	public boolean login(String username, String password) {
		UserInfo u = new UserInfo();
		u.setMail(username);
		String epwd = EncryptUtil.encodeSHA1(password);
		UserInfo userInfo = userDao.getOne(u);
		if (userInfo != null && epwd.equals(userInfo.getPassword())) {
			UserContext.setCurrentUser(userInfo);
			UserContext.setCurrentUserName(username);
			// 设置session
			RequestContext.setSessionAttribute(UserConstants.CURRENT_USER_NAME,
					username);
			if (limitCache != null) {
				limitCache.set(username, LoginLimitContext.getSessionId());
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean validate(String url) {
		return false;
	}

	@Override
	public boolean validate(String username, String url) {
		try {
			ICache<String, Set<String>> cache = CacheManager.getInstance()
					.getCache(UserResourceCache.cacheID);
			Set<String> urls = cache.get(username);
			if (urls.contains(url)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void logout() {
		UserContext.remove();
	}

	@Override
	public UserInfo save(UserInfo user) {
		if (user == null) {
			return null;
		}
		if (StringUtils.isEmpty(user.getMail())) {
			throw new BusinessException("邮箱为空");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			throw new BusinessException("密码为空");
		}
		if (StringUtils.isEmpty(user.getName())) {
			throw new BusinessException("昵称为空");
		}
		user.setId(UUIDUitl.getUuid());
		user.setCreateTime(new Date());
		user.setCreatUser("hbhk");
		user.setUserHeadImg(defaultHead);
		String pwd = user.getPassword();
		pwd = EncryptUtil.encodeSHA1(pwd);
		user.setPassword(pwd);
		return userDao.save(user);
	}

	@Override
	public UserInfo getUser(UserInfo user) {
		if (user == null) {
			return null;
		}
		return userDao.getOne(user);
	}

	public String getDefaultHead() {
		return defaultHead;
	}

	public void setDefaultHead(String defaultHead) {
		this.defaultHead = defaultHead;
	}

}