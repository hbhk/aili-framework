package org.hbhk.aili.mybatis.server.support;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public enum JdbcDataType {
	
	TINYINT(DataType.BYTE),
	SMALLINT(DataType.SHORT),
	INTEGER(DataType.INTEGER),
	INT(DataType.INTEGER),
	BIGINT(DataType.LONG),
	FLOAT(DataType.FLOAT),
	DOUBLE(DataType.DOUBLE),
	CHAR(DataType.STRING),
	VARCHAR(DataType.STRING),
	VARCHAR2(DataType.STRING),
	TEXT(DataType.STRING),
	CLOB(DataType.STRING),
	BLOB(DataType.BYTEArray),
	TIMESTAMP(DataType.DATE),
	DATE(DataType.DATE),
	TIME(DataType.DATE),
	DATETIME(DataType.DATE),
	BOOLEAN(DataType.BYTE);
	
	public final String JAVA_TYPE;
	private static Map<String,String> typeLookup = new HashMap<String,String>();
	
	static {
		for (JdbcDataType type : JdbcDataType.values()) {
			typeLookup.put(type.toString().toLowerCase(),type.JAVA_TYPE);
		}
	}

	private JdbcDataType(String type) {
		this.JAVA_TYPE = type;
	}

	public static String forType(String jdbcType)  {
		jdbcType = jdbcType.toLowerCase();
		String javaType = typeLookup.get(jdbcType);
		if(javaType == null  || "".equals(javaType)){
			javaType = DataType.STRING;
		}
		return javaType;
	}

}
