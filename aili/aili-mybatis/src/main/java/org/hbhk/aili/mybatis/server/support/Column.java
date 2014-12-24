package org.hbhk.aili.mybatis.server.support;

import org.apache.commons.lang.StringUtils;

public class Column {

	// 列名
	private String name;
	private String code;
	// 注释
	private String comment;
	// 数据库列类型
	private String dataType;
	private int length;
	private String nullable;
	// 前端界面的显示grid列
	private String text = "名称";

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
		if (comment == null || "".equals(comment)) {
			return name;
		}
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/*
	 * 根据jdbc类型返回java对应的类型
	 */
	public String getJavaDataType() {
		if (dataType == null || "".equals(dataType)) {
			return DataType.STRING;
		}
		String type = dataType;
		if (dataType.indexOf("(") != -1) {
			type = dataType.substring(0, dataType.indexOf("("));
		}
		return JdbcDataType.forType(type);
	}
	
	public String getJdbcDataType() {
		if (dataType == null || "".equals(dataType)) {
			return "VARCHAR2";
		}
		String type = dataType;
		if (dataType.indexOf("(") != -1) {
			type = dataType.substring(0, dataType.indexOf("("));
		}
		if (dataType.indexOf("2") != -1) {
			type = dataType.substring(0, dataType.indexOf("2"));
		}
		type = type.toUpperCase();
		return type;
	}


	/**
	 * 根据code返回java对应的属性名
	 * 
	 * @return
	 */
	public String getColumnName() {
		String columnName = "";
		if (name == null || "".equals(name)) {
			return columnName;
		}
		String[] names = name.split("_");
		for (int i = 0; i < names.length; i++) {
			String code_ = names[i].toLowerCase();
			if (i >= 1) {
				code_ = StringUtils.capitalize(names[i].toLowerCase());
			}
			columnName += code_;
		}
		return columnName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
