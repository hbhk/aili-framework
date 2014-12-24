package org.hbhk.aili.mybatis.server.support;

import java.text.SimpleDateFormat;
import java.util.List;

public class Table {
	
	// 表名
	private String name;
	// 类名
	private String typeName;
	private String code;
	private String comment;
	private String creator;
	private Long creationDate;
	private Column pk;//主键字段
	private List<Column> columnList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getComment() {
		if(comment == null || "".equals(comment)){
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

	public String getCreationDate() {
		SimpleDateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(creationDate);
	}
	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
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
	
	/** 类名 */
	public String getTypeName(){
		return this.typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
