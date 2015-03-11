package org.hbhk.aili.mybatis.share.util;

import java.io.Serializable;

/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class FieldColumn implements Serializable {
	private static final long serialVersionUID = 2685470419283716180L;

	private String field;
	
	private String column;

	private Class<?> javaType;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}
	
	
}
