package org.hbhk.aili.cache.aop;

import java.util.HashMap;
import java.util.Map;

import org.hbhk.aili.cache.mem.User;
import org.hbhk.aili.cache.server.aop.annotation.CacheKey;
import org.springframework.stereotype.Repository;

@Repository
public class AopUserDao implements IAopUserDao {
	private Map<String, User> users = new HashMap<String, User>();

	public void saveUser(User user) {
		users.put(user.getUserId(), user);
	}

	
	public User getById(String userId, @CacheKey String userId1) {
		System.out.println(userId);
		return users.get(userId);
	}

}