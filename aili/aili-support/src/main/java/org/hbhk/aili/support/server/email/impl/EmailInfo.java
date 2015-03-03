package org.hbhk.aili.support.server.email.impl;

import java.io.Serializable;
import java.util.List;

public class EmailInfo  implements Serializable{

	private static final long serialVersionUID = 5100697778850407790L;
	private String subject;
	private String context;
	private List<String> emails;
	private List<String> attachments;
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
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	
}
