package org.hbhk.aili.security.share.model;

import java.util.Set;

import org.hbhk.aili.mybatis.server.annotation.Column;
import org.hbhk.aili.mybatis.server.annotation.Table;
import org.hbhk.aili.mybatis.share.model.BizBaseModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
@Table("t_aili_resource")
public class ResourceInfo extends BizBaseModel {

	private static final long serialVersionUID = 9125685922301392808L;
	@Column("code")
	private String code;
	// 显示文本
	@Column("text")
	private String text;
	// 是否展开
	@Column("expanded")
	private boolean expanded;
	@Column("parent_code")
	private String parentCode;
	// 显示 顺序
	@Column("priority")
	private int priority;
	// 需要 打开的url
	@Column("url")
	private String url;
	// 显示效果 样式
	@Column("cls")
	private String cls;

	// 子节点
	private Set<String> childrenCodes;

	// 备注
	@Column("memo")
	private String memo;

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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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