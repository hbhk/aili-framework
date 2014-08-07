package org.hbhk.aili.cache.mem;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterDataUpdateContent;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.UpdateSingleCache;

@Component
public class UserDao {
	private static final String NAMESPACE = "ns";
	private Map<String, User> users = new HashMap<String, User>();

	
	public void saveUser(User user) {
		users.put(user.getUserId(), user);
	}

	/**
	 * 当执行getById查询方法时，系统首先会从缓存中获取userId对应的实体 如果实体还没有被缓存，则执行查询方法并将查询结果放入缓存中
	 */
	@ReadThroughSingleCache(namespace = NAMESPACE, expiration = 3600)
	public User getById(String userId ,@ParameterValueKeyProvider String userId1) {
		System.out.println(userId);
		return users.get(userId);
	}

	/**
	 * 当执行updateUser方法时，系统会更新缓存中userId对应的实体 将实体内容更新成@*DataUpdateContent标签所描述的实体
	 */
	@UpdateSingleCache(namespace = NAMESPACE, expiration = 3600)
	public void updateUser(
			@ParameterValueKeyProvider @ParameterDataUpdateContent User user) {
		users.put(user.getUserId(), user);
	}

	/**
	 * 当执行deleteUser方法时，系统会删除缓存中userId对应的实体
	 */
	@InvalidateSingleCache(namespace = NAMESPACE)
	public void deleteUser(@ParameterValueKeyProvider String userId) {
		users.remove(userId);
	}
}