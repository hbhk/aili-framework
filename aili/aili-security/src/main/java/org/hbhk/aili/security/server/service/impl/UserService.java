package org.hbhk.aili.security.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.cache.server.CacheManager;
import org.hbhk.aili.cache.server.ICache;
import org.hbhk.aili.core.server.context.RequestContext;
import org.hbhk.aili.core.share.ex.BusinessException;
import org.hbhk.aili.core.share.util.BeanToMapUtil;
import org.hbhk.aili.core.share.util.EncryptUtil;
import org.hbhk.aili.security.server.cache.LoginLimitCache;
import org.hbhk.aili.security.server.cache.UserCache;
import org.hbhk.aili.security.server.cache.UserResourceCache;
import org.hbhk.aili.security.server.context.LoginLimitContext;
import org.hbhk.aili.security.server.context.UserContext;
import org.hbhk.aili.security.server.dao.IUserDao;
import org.hbhk.aili.security.server.service.IUserService;
import org.hbhk.aili.security.share.define.UserConstants;
import org.hbhk.aili.security.share.model.UserInfo;
import org.hbhk.spring.security.share.vo.UserDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Resource
	private IUserDao userDao;

	private String defaultHead = "/security/default_head.png";

	@Autowired(required = false)
	private LoginLimitCache limitCache;

	@Override
	public UserInfo getMe(String username) {
		UserInfo u = new UserInfo();
		u.setUserName(username);
		Map<String, Object> params = new HashMap<String, Object>();
		BeanToMapUtil.convert( u, params);
		List<UserInfo> users = userDao.get(params);
		if(users!=null && users.size()>0){
			return users.get(0);
		}
		return null;
	}

	@Override
	public boolean login(String username, String password) {
		UserInfo u = new UserInfo();
		u.setUserName(username);
		Map<String, Object> params = new HashMap<String, Object>();
		BeanToMapUtil.convert( u, params);
		List<UserInfo> users = userDao.get(params);
		UserInfo userInfo = null;
		if(users!=null && users.size()>0){
			userInfo = users.get(0);
		}
		String epwd = EncryptUtil.encodeSHA1(password);
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
		if (StringUtils.isEmpty(user.getUserName())) {
			throw new BusinessException("用户名为空");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			throw new BusinessException("密码为空");
		}
		user.setCreateTime(new Date());
		user.setCreatUser("hbhk");
		user.setUserHead(defaultHead);
		String pwd = user.getPassword();
		pwd = EncryptUtil.encodeSHA1(pwd);
		user.setPassword(pwd);
		userDao.insert(user);
		return user;
	}

	@Override
	public UserInfo getUser(UserInfo user) {
		if (user == null) {
			return null;
		}
		return userDao.getById(user.getId()+"");
	}

	public String getDefaultHead() {
		return defaultHead;
	}

	public void setDefaultHead(String defaultHead) {
		this.defaultHead = defaultHead;
	}

	@Override
	public int update(UserInfo user) {
		ICache<String, UserInfo> usercache = CacheManager.getInstance()
				.getCache(UserCache.cacheID);
		String username = UserContext.getCurrentContext().getCurrentUserName();
		if (StringUtils.isNotEmpty(username)) {
			UserInfo cuser = usercache.get(username);
			user.setId(cuser.getId());
			usercache.invalid(username);
		}
		return userDao.update(user);
	}

	@Override
	public UserInfo getOne(UserInfo model) {
		return null;
	}

	@Override
	public List<UserInfo> get(UserInfo model) {
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
        UserDetailsVo user = userDao.findUserDetailsVo(username);
		return user;
	}


}