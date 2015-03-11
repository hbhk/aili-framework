package org.hbhk.aili.jms.share.pojo;
/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class AuthInfo {
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;

	/**
	 * Get the 'username' element value. 用户名
	 * 
	 * @return value
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the 'username' element value. 用户名
	 * 
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the 'password' element value. 密码
	 * 
	 * @return value
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the 'password' element value. 密码
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
