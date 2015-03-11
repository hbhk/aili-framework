package org.hbhk.aili.mybatis.server.support;


/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class ColumnInfo {

	// 列名
	private String name;
	// 注释
	private String comment;
	// 数据库列类型
	private String dataType;
	
	private int length;
	
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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
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
