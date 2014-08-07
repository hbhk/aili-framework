package org.hbhk.aili.security.share.pojo;

import java.util.List;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Entity;
import org.hbhk.aili.orm.server.annotation.JoinColumn;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.share.model.BaseInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
@Entity
@Tabel("t_aili_resource")
public class ResourceInfo extends BaseInfo {

	private static final long serialVersionUID = 9125685922301392808L;
	@Column("code")
	private String code;
	// 显示文本
	@Column("text")
	private String text;
	// 是否展开
	@Column("expanded")
	private boolean expanded;
	// 菜单类型
	@Column("type")
	private String type;
	@Column("enable")
	private int enable;
	@Column("parentCode")
	private String parentCode;
	// 显示 顺序
	@Column("priority")
	private int priority;
	// 需要 打开的url
	@Column("url")
	private String url = "getMenu.ctrl";
	// 显示效果 样式
	@Column("meun")
	private String classes = "meun";

	// 子节点
	@Column("children")
	@JoinColumn
	private List<ResourceInfo> children;
	// 是否有子节点
	@Column("hasChildren")
	private boolean hasChildren;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
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

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
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