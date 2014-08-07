package org.hbhk.aili.orm.server;

import java.io.Serializable;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.PrimaryKey;
import org.hbhk.aili.orm.server.annotation.Tabel;

@Tabel("t_aili_user")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 54122579931594962L;
	@PrimaryKey
	@Column("id")
	private String id;
	@Column("id")
	private int enable;
	@Column("id")
	private String password;
	@Column("id")
	private String username;
	@Column("id")
	private String name;
	@Column("id")
	private String mail;
	@Column("id")
	private String gender;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

}