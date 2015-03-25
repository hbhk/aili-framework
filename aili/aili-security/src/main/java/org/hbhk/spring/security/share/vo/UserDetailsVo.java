package org.hbhk.spring.security.share.vo;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 当前用户的所有信息，基于SpringSecurity
 * 
 * @since
 */
public class UserDetailsVo implements UserDetails {

	private static final long serialVersionUID = -967256670810173952L;

	/** 用户的ID */
	private Long userId;

	/** 用户的账号 */
	private String username;

	/** 用户的密码 */
	private String password;

	private Set<String> privilegeUrls;
	
	public UserDetailsVo() {
	}

	public UserDetailsVo(Long userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO 待定
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO 待定
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO 待定
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO 待定
		return true;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Set<String> getPrivilegeUrls() {
		return privilegeUrls;
	}

	public void setPrivilegeUrls(Set<String> privilegeUrls) {
		this.privilegeUrls = privilegeUrls;
	}

	
}
