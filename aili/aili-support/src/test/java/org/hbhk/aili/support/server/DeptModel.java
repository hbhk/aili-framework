package org.hbhk.aili.support.server;

import java.util.Date;

public class DeptModel {
	private String deptName;
	private String deptCode;
	private String sendFileName;
	private Date sendDate;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getSendFileName() {
		return sendFileName;
	}

	public void setSendFileName(String sendFileName) {
		this.sendFileName = sendFileName;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

}
