package org.hbhk.aili.mybatis.server.support;

import java.io.Serializable;

/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class Page implements Serializable {

	private static final long serialVersionUID = -4902532758803288395L;

	private int pageNum = 0;
	private int pageSize = 10;

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
