package org.hbhk.aili.security.server.cache;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.hbhk.aili.cache.server.CacheSupport;
import org.hbhk.aili.security.server.dao.IRoleDao;
import org.hbhk.aili.security.server.dao.IUserDao;
import org.springframework.stereotype.Component;

@Component
public class UserResourceCache extends CacheSupport<Set<String>> {

	public final static String cacheID = "userResource";
	@Resource
	private IRoleDao roleDao;
	@Resource
	private IUserDao userDao;

	@Override
	public Set<String> doSet(String key) {
		Set<String> res = new HashSet<String>();

		return res;
	}

	@Override
	public String getCacheId() {
		return cacheID;
	}

}
