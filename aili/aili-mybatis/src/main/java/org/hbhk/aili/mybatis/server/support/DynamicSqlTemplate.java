package org.hbhk.aili.mybatis.server.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.core.share.util.AnnotationScanningUtil;
import org.hbhk.aili.mybatis.server.annotation.Column;
import org.hbhk.aili.mybatis.server.annotation.Table;
import org.hbhk.aili.mybatis.server.handler.DefaultNameHandler;
import org.hbhk.aili.mybatis.server.handler.INameHandler;
import org.hbhk.aili.mybatis.share.util.SqlUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
@Component
public class DynamicSqlTemplate implements InitializingBean {

	@Autowired(required=false)
	private INameHandler nameHandler;
	
	/**
	 * 多个包用,分割
	 */
	@Value("${auto.scan.table.packages}")
	private String autoTablePath;

	private static Map<String, ModelInfo> tabs = new HashMap<String, ModelInfo>();

	public String insert(Object obj) {
		Class<?> cls = obj.getClass();
		String clsName = cls.getName();
		Table table = cls.getAnnotation(Table.class);
		boolean dynamicInsert = table.dynamicInsert();
		StringBuilder sql = new StringBuilder();
		ModelInfo tableInfo = tabs.get(clsName);
		if(dynamicInsert){
			//动态插入
			Field[] fields = tableInfo.getColumnFields();
			sql.append("insert into ");
			sql.append(tableInfo.getTable() + " (");
			String pk = tableInfo.getPk();
			sql.append(pk+",");
			StringBuilder args = new StringBuilder();
			args.append("(");
			args.append("#{"+pk+"},");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String name = field.getName();
				Object value = null;
				try {
					value = PropertyUtils.getProperty(obj, name);
				} catch (Exception e) {
					throw new RuntimeException("获取属性值错误:", e);
				}
				if (value == null) {
					continue;
				}
				Column col = field.getAnnotation(Column.class);
				String columnName = col.value();
				if (StringUtils.isEmpty(columnName)) {
					continue;
				}
				sql.append(columnName);
				args.append("#{"+columnName+"}");
				sql.append(",");
				args.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
			args.deleteCharAt(args.length() - 1);
			args.append(")");
			sql.append(")");
			sql.append(" values ");
			sql.append(args);
		}else{
			List<String> fieldList = tableInfo.getFieldList();
			sql.append("insert into ");
			sql.append(tableInfo.getTable() + " (");
			sql.append(tableInfo.getColumns() + ") ");
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
		}
		return sql.toString();
	}

	public String update(Object obj) {
		Class<?> cls = obj.getClass();
		String clsName = cls.getName();
		ModelInfo tableInfo = tabs.get(clsName);
		StringBuilder sql = new StringBuilder();
		Table table = cls.getAnnotation(Table.class);
		boolean dynamicUpdate = table.dynamicUpdate();
		if(dynamicUpdate){
			//动态更新
			sql.append("update ");
			sql.append(tableInfo.getTable() + " set ");
			Field[] fields = tableInfo.getColumnFields();
			String pk = tableInfo.getPk();
			for (Field field : fields) {
				String name = field.getName();
				Object value = null;
				try {
					value = PropertyUtils.getProperty(obj, name);
				} catch (Exception e) {
					throw new RuntimeException("获取属性值错误:", e);
				}
				if (value == null) {
					continue;
				}
				Column col = field.getAnnotation(Column.class);
				String columnName = col.value();
				if (StringUtils.isEmpty(columnName)) {
					continue;
				}
				if (!pk.equalsIgnoreCase(columnName)) {
					sql.append(columnName);
					sql.append(" = ");
					sql.append("#{"+columnName+"}");
					sql.append(",");
				} 
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" where "+pk+"=#{"+pk+"}");
		}else{
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
			sql.append(" where "+pk+"=#{"+pk+"}");
		}
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
			ModelInfo tableInfo = tabs.get(getKey());
			String pk = tableInfo.getPk();
			Map<String, String> fieldColumn = tableInfo.getFieldColumnMap();
			sql.append("where ");
			for (int i = 0; i < keys.size(); i++) {
				String field = keys.toArray(new String[]{})[i];
				String col = fieldColumn.get(field);
				if(StringUtils.isEmpty(col)){
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
	
	@SuppressWarnings("unchecked")
	public String getPage(Map<String, Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select *from ");
		sql.append(getTableName() +" ");
		Set<String>  keys = params.keySet();
		Map<String, Object> newParams =new HashMap<String, Object>(); 
		for (String key : keys) {
		    if(key.startsWith("param")){
				continue;
			}
		    Object obj = params.get(key);
		    if(obj instanceof Map){
			  Map<String, Object> map = (Map<String, Object>) obj;
			  for (String interKey : map.keySet()) {
				  newParams.put(interKey, map.get(interKey));
			}
		   }else{
			   newParams.put(key, obj);
		   }
		}
		Set<String>  newKeys = newParams.keySet();
		params.putAll(newParams);
		if(newKeys.size() > 0){
			ModelInfo tableInfo = tabs.get(getKey());
			String pk = tableInfo.getPk();
			Map<String, String> fieldColumn = tableInfo.getFieldColumnMap();
			sql.append("where ");
			int num = 0;
			for (int i = 0; i < newKeys.size(); i++) {
				String field = newKeys.toArray(new String[]{})[i];
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
					if(num == 0){
						sql.append(col+"=#{"+field+"}");
						num++;
					}else{
						sql.append(" and "+col+"=#{"+field+"}");
						num++;
					}
				}
				
			}
		}
		Integer pageNum = (Integer) newParams.get("pageNum");
		if(pageNum == null || pageNum <= 0 ){
			pageNum = 1;
		}
		Integer pageSize = (Integer) newParams.get("pageSize");
		if(pageSize == null){
			pageSize = 10;
		}
		int start = 0;
		if(pageNum > 0){
			start = (pageSize-1)*pageNum;
		}
		sql.append(" limit "+start+","+pageSize);
		return sql.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getPagination(Map<String, Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select *from ");
		sql.append(getTableName() +" ");
		Set<String>  keys = params.keySet();
		Map<String, Object> newParams =new HashMap<String, Object>(); 
		for (String key : keys) {
		    if(key.startsWith("param")){
				continue;
			}
		    Object obj = params.get(key);
		    if(obj instanceof Map){
			  Map<String, Object> map = (Map<String, Object>) obj;
			  for (String interKey : map.keySet()) {
				  newParams.put(interKey, map.get(interKey));
			}
		   }else{
			   newParams.put(key, obj);
		   }
		}
		Set<String>  newKeys = newParams.keySet();
		params.putAll(newParams);
		if(newKeys.size() > 0){
			ModelInfo tableInfo = tabs.get(getKey());
			String pk = tableInfo.getPk();
			Map<String, String> fieldColumn = tableInfo.getFieldColumnMap();
			sql.append("where ");
			int num = 0;
			for (int i = 0; i < newKeys.size(); i++) {
				String field = newKeys.toArray(new String[]{})[i];
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
					if(num == 0){
						sql.append(col+"=#{"+field+"}");
						num++;
					}else{
						sql.append(" and "+col+"=#{"+field+"}");
						num++;
					}
				}
				
			}
		}
		return sql.toString();
	}
	
	public String getPageTotalCount(Map<String, Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from ");
		sql.append(getTableName() +" ");
		Set<String>  keys = params.keySet();
		if(keys.size() > 0){
			ModelInfo tableInfo = tabs.get(getKey());
			String pk = tableInfo.getPk();
			Map<String, String> fieldColumn = tableInfo.getFieldColumnMap();
			sql.append("where ");
			int num = 0;
			for (int i = 0; i < keys.size(); i++) {
				String field = keys.toArray(new String[]{})[i];
				String col = fieldColumn.get(field);
				if(StringUtils.isEmpty(col)&& !field.equals(pk)){
					continue;
				}
				if(field.equals(pk)){
					sql.append(pk+"=#{"+pk+"}");
				}else{
					if(num == 0){
						sql.append(col+"=#{"+field+"}");
						num++;
					}else{
						sql.append(" and "+col+"=#{"+field+"}");
						num++;
					}
				}
				
			}
		}
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
		if(nameHandler == null){
			nameHandler = new DefaultNameHandler();
		}
		if (StringUtils.isNotEmpty(autoTablePath)) {
			String[] autoTablePaths = autoTablePath.split(",");
			List<Class<?>> tabClasses = AnnotationScanningUtil.getAnnotatedClasses(Table.class, autoTablePaths);
			if (tabClasses != null) {
				for (Class<?> tab : tabClasses) {
					Field[] fields = SqlUtil.getColumnFields(tab);
					ModelInfo tableInfo = new ModelInfo();

					List<String> columnList = new ArrayList<String>();
					List<String> fieldList = new ArrayList<String>();
					StringBuilder columnFields = new StringBuilder();
					String pk = SqlUtil.getpriKey(fields);
					pk = nameHandler.getPrimaryName(pk);
					String tabName = tab.getAnnotation(Table.class).value();
					tabName = nameHandler.getTableName(tabName);
					Map<String, String> fieldColumnMap = new HashMap<String, String>();
					for (Field field : fields) {
						Column col = field.getAnnotation(Column.class);
						String colName = col.value();
//						if(colName.equals(pk)){
//							continue;
//						}
						String fieldName = field.getName();
						colName = nameHandler.getColumnName(colName);
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
		ModelInfo modelInfo = tabs.get(getKey());
		if(modelInfo==null){
			throw new RuntimeException("未配置model信息:"+getKey());
		}
		return modelInfo.getTable();
	}
	private String getKey() {
		return GnericInterfaceTypeContext.getType().getName();
	}
}
