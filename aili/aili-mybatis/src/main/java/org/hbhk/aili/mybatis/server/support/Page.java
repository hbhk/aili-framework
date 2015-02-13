package org.hbhk.aili.mybatis.server.support;

import java.io.Serializable;

public class Page implements Serializable {

	private static final long serialVersionUID = -4902532758803288395L;

	private int pageNum;
	private int pageSize;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
