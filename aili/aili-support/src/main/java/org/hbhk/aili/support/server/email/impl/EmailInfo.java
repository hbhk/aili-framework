package org.hbhk.aili.support.server.email.impl;

import java.io.Serializable;

public class EmailInfo  implements Serializable{

	private static final long serialVersionUID = 5100697778850407790L;
	private String subject;
	private String context;
	private String email;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
