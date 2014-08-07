package org.hbhk.aili.security.server.cache;

import javax.annotation.Resource;

import org.hbhk.aili.cache.server.CacheSupport;
import org.hbhk.aili.security.server.dao.IUserDao;
import org.hbhk.aili.security.share.pojo.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserCache extends CacheSupport<UserInfo> {

	public final static String cacheID = "user";
	@Resource
	private IUserDao userDao;

	@Override
	public UserInfo doSet(String key) {
		UserInfo u = new UserInfo();
		u.setMail(key);
		return userDao.getOne(u);
	}

	@Override
	public String getCacheId() {
		return cacheID;
	}

}
