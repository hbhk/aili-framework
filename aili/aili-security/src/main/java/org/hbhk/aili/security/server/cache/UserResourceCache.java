package org.hbhk.aili.security.server.cache;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.cache.server.CacheSupport;
import org.hbhk.aili.security.server.dao.IRoleDao;
import org.hbhk.aili.security.server.dao.IUserDao;
import org.hbhk.aili.security.share.pojo.ResourceInfo;
import org.hbhk.aili.security.share.pojo.RoleInfo;
import org.hbhk.aili.security.share.pojo.UserInfo;
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
		UserInfo u = new UserInfo();
		u.setUsername(key);
		UserInfo user = userDao.getOne(u);
		Set<String> res = new HashSet<String>();
		if (user != null && user.getRoles() != null
				&& user.getRoles().size() > 0) {
			Set<RoleInfo> roles = user.getRoles();
			for (RoleInfo roleInfo : roles) {
				RoleInfo role = new RoleInfo();
				role.setCode(roleInfo.getCode());
				role = roleDao.getOne(role);
				if (role != null) {
					Set<ResourceInfo> resourceInfos = role.getResources();
					if (resourceInfos != null && resourceInfos.size() > 0) {
						for (ResourceInfo resourceInfo : resourceInfos) {
							String url = resourceInfo.getUrl();
							if (StringUtils.isNotEmpty(url)) {
								res.add(resourceInfo.getUrl());
							}
						}
					}
				}

			}
		}

		return res;
	}

	@Override
	public String getCacheId() {
		return cacheID;
	}

}
