package org.hbhk.aili.security.server.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hbhk.aili.cache.server.CacheSupport;
import org.hbhk.aili.core.share.util.BeanToMapUtil;
import org.hbhk.aili.security.server.dao.IUserDao;
import org.hbhk.aili.security.share.model.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserCache extends CacheSupport<UserInfo> {

	public final static String cacheID = "user";
	@Resource
	private IUserDao userDao;

	@Override
	public UserInfo doSet(String key) {
		UserInfo u = new UserInfo();
		u.setUserName(key);
		Map<String, Object> params = new HashMap<String, Object>();
		BeanToMapUtil.convert(u, params);
		List<UserInfo> users = userDao.get(params);
		UserInfo userInfo = null;
		if (users != null && users.size() > 0) {
			userInfo = users.get(0);
		}
		return userInfo;
	}

	@Override
	public String getCacheId() {
		return cacheID;
	}

}
