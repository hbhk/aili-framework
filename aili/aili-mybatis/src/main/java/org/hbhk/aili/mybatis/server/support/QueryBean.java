package org.hbhk.aili.mybatis.server.support;

import java.util.Map;

/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class QueryBean {
	public static final int DEFULT_START = 0;
	public static final int DEFULT_SIZE = 10;

	private Page page = new Page();
	/**
	 * 查询参数
	 */
	private Map<String, Object> paraMap;

	public Map<String, Object> getParaMap() {
		return paraMap;
	}

	public void setParaMap(Map<String, Object> paraMap) {
		this.paraMap = paraMap;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
