package org.hbhk.aili.security.share.pojo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 当前用户的所有信息，基于SpringSecurity
 * 
 * @since
 */
public class UserDetailsCommand implements UserDetails {

	private static final long serialVersionUID = -967256670810173952L;

	/** 用户的ID */
	private Long userId;

	/** 用户的账号 */
	private String username;

	/** 用户的密码 */
	private String password;

	/** 当前组织ID */
	private String currentOrgId;

	/** 当前组织名称 */
	private String currentOrgName;

	public UserDetailsCommand() {
	}

	public UserDetailsCommand(Long userId, String username, String password) {
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

	public String getCurrentOrgId() {
		return currentOrgId;
	}

	public void setCurrentOrgId(String currentOrgId) {
		this.currentOrgId = currentOrgId;
	}

	public String getCurrentOrgName() {
		return currentOrgName;
	}

	public void setCurrentOrgName(String currentOrgName) {
		this.currentOrgName = currentOrgName;
	}

}
