package org.hbhk.aili.security.share.pojo;

import java.util.Set;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Entity;
import org.hbhk.aili.orm.server.annotation.JoinColumn;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.share.model.BaseInfo;

@Entity
@Tabel("t_aili_user")
public class UserInfo extends BaseInfo {

	private static final long serialVersionUID = 54122579931594962L;
	@Column("enable")
	private int enable;
	@Column("password")
	private String password;
	@Column("username")
	private String username;
	@Column("userHeadImg")
	private String userHeadImg;
	@Column("name")
	private String name;
	@Column("mail")
	private String mail;
	@Column("remail")
	private String remail;
	@Column("gender")
	private String gender;
	@Column("roles")
	@JoinColumn
	private Set<RoleInfo> roles;

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<RoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleInfo> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserHeadImg() {
		return userHeadImg;
	}

	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = userHeadImg;
	}

	public String getRemail() {
		return remail;
	}

	public void setRemail(String remail) {
		this.remail = remail;
	}

}