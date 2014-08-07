package org.hbhk.aili.cache.mem;


import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

public class User implements Serializable {
	private static final long serialVersionUID = 7517080513591583073L;
	private String userId;
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@CacheKeyMethod
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}