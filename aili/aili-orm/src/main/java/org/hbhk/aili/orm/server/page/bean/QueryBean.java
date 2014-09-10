package org.hbhk.aili.orm.server.page.bean;

import java.util.Map;

import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;

public class QueryBean {
	public static final int DEFULT_START = 0;
	public static final int DEFULT_SIZE = 10;
	
	private Page page = new Page();
	/**
	 * 排序
	 */
	private Sort[] sorts;
	/**
	 * 查询参数
	 */
	private Map<String, Object> paraMap;
	
	public Sort[] getSorts() {
		return sorts;
	}
	public void setSorts(Sort[] sorts) {
		this.sorts = sorts;
	}
	public Map<String, Object> getParaMap() {
		return paraMap;
	}
	public void setParaMap(Map<String, Object> paraMap) {
		this.paraMap = paraMap;
	}
	
	public Page getPage(){
		return page;
	}
	
	public void setPage(Page page){
		this.page = page;
	}
	
}
