package org.hbhk.aili.mybatis.server.support;


public class Column {

	// 列名
	private String name;
	// 注释
	private String comment;
	// 数据库列类型
	private String dataType;

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



}
