package org.hbhk.aili.mybatis.server.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.core.server.annotation.AnnotationScanning;
import org.hbhk.aili.mybatis.server.annotation.Column;
import org.hbhk.aili.mybatis.server.annotation.Table;
import org.hbhk.aili.mybatis.share.model.TableInfo;
import org.hbhk.aili.mybatis.share.util.SqlUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MybatisSqlTemplate implements InitializingBean {

	/**
	 * 多个包用,分割
	 */
	@Value("${auto.scan.table.packages}")
	private String autoTablePath;

	private static Map<String, TableInfo> tabs = new HashMap<String, TableInfo>();

	public String insert(Object obj) {
		Class<?> cls = obj.getClass();
		String clsName = cls.getName();
		TableInfo tableInfo = tabs.get(clsName);
		List<String> fieldList = tableInfo.getFieldList();
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		sql.append(tableInfo.getTable() + " (");
		sql.append(tableInfo.getColumnList() + ") ");
		sql.append("values(");
		for (int i = 0; i < fieldList.size(); i++) {
			String col = fieldList.get(i);
			if ((i + 1) == fieldList.size()) {
				sql.append("#{"+col+"}");
			} else {
				sql.append("#{"+col+"},");
			}
		}
		sql.append(")");
		return sql.toString();
	}

	public String update(Object obj) {
		Class<?> cls = obj.getClass();
		String clsName = cls.getName();
		TableInfo tableInfo = tabs.get(clsName);
		StringBuilder sql = new StringBuilder();
		sql.append("update ");
		sql.append(tableInfo.getTable() + " set ");
		List<String> fieldList = tableInfo.getFieldList();
		List<String> columnList = tableInfo.getColumnList();
		String pk = tableInfo.getPk();
		for (int i = 0; i < fieldList.size(); i++) {
			String field = fieldList.get(i);
			String col = columnList.get(i);
			if ((i + 1) == fieldList.size()) {
				sql.append(col+"=#{"+field+"}");
			} else {
				sql.append(col+"=#{"+field+"},");
			}
		}
		sql.append(" where "+pk+"=#{id}");
		return sql.toString();
	}

	public String getById(Map<String, Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select *from ");
		sql.append(getTableName());
		sql.append(" where id = #{id}");
		return sql.toString();
	}

	public String get(Map<String, Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select *from ");
		sql.append(getTableName() +" ");
		Set<String>  keys = params.keySet();
		if(keys.size() > 0){
			TableInfo tableInfo = tabs.get(getKey());
			String pk = tableInfo.getPk();
			Map<String, String> fieldColumn = tableInfo.getFieldColumnMap();
			sql.append("where ");
			for (int i = 0; i < keys.size(); i++) {
				String field = keys.toArray(new String[]{})[i];
				String col = fieldColumn.get(field);
				if(StringUtils.isEmpty(col)&& !field.equals(pk)){
					continue;
				}
				if(field.equals(pk)){
					sql.append(pk+"=#{"+pk+"}");
				}else{
					if(i == 0){
						sql.append(col+"=#{"+field+"}");
					}else{
						sql.append(" and "+col+"=#{"+field+"}");
					}
				}
				
			}
		}
		
		return sql.toString();
	}
	
	public String getPage(Map<String, Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select *from ");
		sql.append(getTableName() +" ");
		Set<String>  keys = params.keySet();
		if(keys.size() > 0){
			TableInfo tableInfo = tabs.get(getKey());
			String pk = tableInfo.getPk();
			Map<String, String> fieldColumn = tableInfo.getFieldColumnMap();
			sql.append("where ");
			for (int i = 0; i < keys.size(); i++) {
				String field = keys.toArray(new String[]{})[i];
				if("start".equals(field) || "size".equals(field)){
					continue;
				}
				String col = fieldColumn.get(field);
				if(StringUtils.isEmpty(col)&& !field.equals(pk)){
					continue;
				}
				if(field.equals(pk)){
					sql.append(pk+"=#{"+pk+"}");
				}else{
					if(i == 0){
						sql.append(col+"=#{"+field+"}");
					}else{
						sql.append(" and "+col+"=#{"+field+"}");
					}
				}
				
			}
		}
		Integer start = (Integer) params.get("start");
		if(start==null){
			start = 0;
		}
		Integer size = (Integer) params.get("size");
		if(size==null){
			size = 10;
		}
		sql.append(" limit "+start+","+size);
		return sql.toString();
	}

	public String deleteById(Map<String, Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append(getTableName());
		sql.append(" where id = #{id}");
		return sql.toString();
	}

	public String updateStatusById(Map<String, Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ");
		sql.append(getTableName());
		sql.append(" set status = #{status} ");
		sql.append("where id = #{id}");
		return sql.toString();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isNotEmpty(autoTablePath)) {
			String[] autoTablePaths = autoTablePath.split(",");
			List<Class<?>> tabClasses = AnnotationScanning.getInstance()
					.getAnnotatedClasses(Table.class, autoTablePaths);
			if (tabClasses != null) {
				for (Class<?> tab : tabClasses) {
					Field[] fields = SqlUtil.getColumnFields(tab);
					TableInfo tableInfo = new TableInfo();

					List<String> columnList = new ArrayList<String>();
					List<String> fieldList = new ArrayList<String>();
					StringBuilder columnFields = new StringBuilder();
					String pk = SqlUtil.getpriKey(fields);
					String tabName = tab.getAnnotation(Table.class).value();
					Map<String, String> fieldColumnMap = new HashMap<String, String>();
					for (Field field : fields) {
						Column col = field.getAnnotation(Column.class);
						String colName = col.value();
						if(colName.equals(pk)){
							continue;
						}
						String fieldName = field.getName();
						columnList.add(colName);
						fieldList.add(fieldName);
						columnFields.append(colName + ",");
						fieldColumnMap.put(fieldName, colName);
					}
					String columns = columnFields.substring(0,
							columnFields.length() - 1);
					tableInfo.setColumnFields(fields);
					tableInfo.setCls(tab);
					tableInfo.setColumnList(columnList);
					tableInfo.setFieldList(fieldList);
					tableInfo.setColumns(columns);
					tableInfo.setTable(tabName);
					tableInfo.setPk(pk);
					tableInfo.setFieldColumnMap(fieldColumnMap);
					tabs.put(tab.getName(), tableInfo);

				}
			}
		}
	}

	private String getTableName() {
		String tab = getType().getAnnotation(Table.class).value();
		return tab;
	}

	private Class<?> getType() {
		return GnericInterfaceTypeContext.getType();
	}
	private String getKey() {
		return GnericInterfaceTypeContext.getType().getName();
	}
}
