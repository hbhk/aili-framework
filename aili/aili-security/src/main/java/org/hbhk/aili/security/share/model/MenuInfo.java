package org.hbhk.aili.security.share.model;

import java.io.Serializable;
import java.util.Set;

public class MenuInfo implements Serializable {

	private static final long serialVersionUID = 6738222380954849117L;
	private String code;
	// 显示文本
	private String text;
	// 是否展开
	private boolean expanded;
	
	private String parentCode;
	// 显示 顺序
	private int priority;
	// 需要 打开的url
	private String url;
	// 显示效果 样式
	private String cls;
	// 子节点
	private Set<String> childrenCodes;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public Set<String> getChildrenCodes() {
		return childrenCodes;
	}

	public void setChildrenCodes(Set<String> childrenCodes) {
		this.childrenCodes = childrenCodes;
	}

}