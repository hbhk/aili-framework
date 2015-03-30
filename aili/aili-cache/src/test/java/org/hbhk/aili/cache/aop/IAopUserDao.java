package org.hbhk.aili.cache.aop;

import org.hbhk.aili.cache.mem.User;
import org.hbhk.aili.cache.server.aop.annotation.CacheKey;
import org.hbhk.aili.cache.server.aop.annotation.ReadWriteCache;

public interface IAopUserDao {
	void saveUser(User user);

	@ReadWriteCache(prefix = "sdff", expire = 3600)
	User getById(String userId, @CacheKey String userId1);
}
