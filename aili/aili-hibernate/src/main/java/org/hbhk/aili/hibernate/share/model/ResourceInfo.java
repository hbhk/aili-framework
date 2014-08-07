package org.hbhk.aili.hibernate.share.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "t_aili_resource")
public class ResourceInfo implements java.io.Serializable {

	private static final long serialVersionUID = 9125685922301392808L;
	// 节点 id
	@Column(name = "id", length = 255)
	private String id;
	@Column(name = "code", length = 255)
	private String code;
	// 显示文本
	@Column(name = "text", length = 255)
	private String text;
	// 是否展开
	@Column(name = "expanded", length = 1)
	private boolean expanded;
	// 菜单类型
	@Column(name = "type", length = 255)
	private String type;

	@Column(name = "active", length = 1)
	private String active;
	@Column(name = "parentCode", length = 255)
	private String parentCode;
	// 显示 顺序
	@Column(name = "priority", length = 2)
	private int priority;
	// 需要 打开的url
	@Column(name = "priority", length = 255)
	private String url;
	// 显示效果 样式
	@Column(name = "css", length = 255)
	private String css;
	// 子节点
	private List<ResourceInfo> children;
	// 是否有子节点
	@Column(name = "hasChildren", length = 1)
	private boolean hasChildren;
	// 备注
	@Column(name = "hasChildren", length = 1000)
	private String memo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public List<ResourceInfo> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceInfo> children) {
		this.children = children;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}