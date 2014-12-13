package org.hbhk.aili.orm.server.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.ColumnTranslator;
import org.hbhk.aili.orm.share.util.SqlUtil;

public class UpperCaseColumnTranslator implements ColumnTranslator {

	private Map<String,String> columnMap = new HashMap<String, String>();
	
	public UpperCaseColumnTranslator(){}
	
	public UpperCaseColumnTranslator(Class<?> clazz){
		setModelClass(clazz);
	}
	
	public String toColumnName(String attribute) {
		return columnMap.get(attribute);
	}

	public void setModelClass(Class<?> clazz) {
		try {
			Field[] fields = SqlUtil.getColumnFields(clazz);
			for(Field field: fields){
				Column col = field.getAnnotation(Column.class);
				if(col!= null){		
					String colName = col.value().toLowerCase();
					columnMap.put(field.getName(),colName);
				}else{
					columnMap.put(field.getName(), field.getName().toLowerCase());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
