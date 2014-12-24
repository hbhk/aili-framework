package org.hbhk.aili.mybatis.server.support;

import java.util.List;

public class Table {

	// 表名
	private String name;
	private String comment;
	private String creator;
	private Column pk;// 主键字段
	private List<Column> columnList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		if (comment == null || "".equals(comment)) {
			return name;
		}
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Column getPk() {
		return pk;
	}

	public void setPk(Column pk) {
		this.pk = pk;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

}
