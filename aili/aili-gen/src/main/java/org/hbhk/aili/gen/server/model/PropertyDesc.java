package org.hbhk.aili.gen.server.model;

public class PropertyDesc {

	/**
	 * 属性字段名称
	 */
	private String fieldName;

	private String jetName;

	/**
	 * 表字段名称
	 */
	private String columnName;

	/**
	 * 属性字段类型
	 */
	private String fieldType;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getJetName() {
		return jetName;
	}

	public void setJetName(String jetName) {
		this.jetName = jetName;
	}

	
}
