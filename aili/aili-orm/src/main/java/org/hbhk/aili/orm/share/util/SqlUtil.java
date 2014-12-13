package org.hbhk.aili.orm.share.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Id;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.server.handler.INameHandler;
import org.hbhk.aili.orm.share.model.SqlContext;

/**
 * sql辅助为类
 */
public class SqlUtil {

	public static Field[] getColumnFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Column col = field.getAnnotation(Column.class);
			if (col != null) {
				list.add(field);
			}
		}
		if (clazz.getSuperclass() != null) {
			Class<?> superClass = clazz.getSuperclass();
			fields = superClass.getDeclaredFields();
			for (Field field : fields) {
				Column col = field.getAnnotation(Column.class);
				if (col != null) {
					list.add(field);
				}
			}
		}
		return list.toArray(new Field[] {});
	}

	/**
	 * 构建insert语句
	 * 
	 * @param entity
	 *            实体映射对象
	 * @param INameHandler
	 *            名称转换处理器
	 * @return
	 */
	public static SqlContext buildInsertSql(Object bean,
			INameHandler nameHandler) {
		Class<?> clazz = bean.getClass();
		Tabel tab = clazz.getAnnotation(Tabel.class);
		if (tab == null) {
			return null;
		}
		String tableName = tab.value();
		tableName = nameHandler.getTableName(tableName);
		Field[] fields = getColumnFields(clazz);

		String primaryName = getpriKey(fields);
		primaryName = nameHandler.getPrimaryName(primaryName);
		if (primaryName == null) {
			throw new RuntimeException(clazz.getName() + "未找到主键");
		}
		StringBuilder sql = new StringBuilder("insert into ");
		List<Object> params = new ArrayList<Object>();
		sql.append(tableName);
		// 获取属性信息
		sql.append("(");
		StringBuilder args = new StringBuilder();
		args.append("(");
		for (Field field : fields) {
			String name = field.getName();
			Object value = null;
			try {
				value = PropertyUtils.getProperty(bean, name);
			} catch (Exception e) {
				throw new RuntimeException("获取属性值错误:", e);
			}
			if (value == null) {
				continue;
			}
			Column col = field.getAnnotation(Column.class);
			String columnName = col.value();
			columnName = nameHandler.getColumnName(columnName);
			if (StringUtils.isEmpty(columnName)) {
				continue;
			}
			sql.append(columnName);
			args.append("?");
			params.add(value);
			sql.append(",");
			args.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		args.deleteCharAt(args.length() - 1);
		args.append(")");
		sql.append(")");
		sql.append(" values ");
		sql.append(args);
		return new SqlContext(sql, primaryName, params);
	}

	public static String getpriKey(Field[] fields) {
		String pk= null;
		for (Field field : fields) {
			Id id = field.getAnnotation(Id.class);
			if (id != null) {
				pk =  field.getAnnotation(Column.class).value();
			}
		}
		if(pk == null){
			throw new RuntimeException("未找到主键");
		}
		return pk;

	}

	/**
	 * 构建更新sql
	 * 
	 * @param entity
	 * @param INameHandler
	 * @return
	 */
	public static SqlContext buildUpdateSql(Object bean,
			INameHandler nameHandler) {
		Class<?> clazz = bean.getClass();
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		Tabel tab = clazz.getAnnotation(Tabel.class);
		if (tab == null) {
			return null;
		}
		String tableName = tab.value();
		tableName = nameHandler.getTableName(tableName);
		Field[] fields = getColumnFields(clazz);
		String primaryName = getpriKey(fields);
		primaryName = nameHandler.getPrimaryName(primaryName);
		if (primaryName == null) {
			throw new RuntimeException(clazz.getName() + "未找到主键");
		}
		sql.append("update ");
		sql.append(tableName);
		sql.append(" set ");
		Object primaryValue = null;
		for (Field field : fields) {
			String name = field.getName();
			Object value = null;
			try {
				value = PropertyUtils.getProperty(bean, name);
			} catch (Exception e) {
				throw new RuntimeException("获取属性值错误:", e);
			}
			Column col = field.getAnnotation(Column.class);
			String columnName = col.value();
			columnName = nameHandler.getColumnName(columnName);
			if (StringUtils.isEmpty(columnName)) {
				continue;
			}
			if (!primaryName.equalsIgnoreCase(columnName)) {
				sql.append(columnName);
				sql.append(" = ");
				sql.append("?");
				params.add(value);
				sql.append(",");
			} else {
				primaryValue = value;
			}
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" where ");
		sql.append(primaryName);
		sql.append(" = ?");
		params.add(primaryValue);
		return new SqlContext(sql, primaryName, params);
	}

	/**
	 * 构建更新sql
	 * 
	 * @param entity
	 * @param INameHandler
	 * @return
	 */
	public static SqlContext buildDeleteSql(Object bean,
			INameHandler nameHandler) {
		Class<?> clazz = bean.getClass();
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		Tabel tab = clazz.getAnnotation(Tabel.class);
		if (tab == null) {
			return null;
		}
		String tableName = tab.value();
		tableName = nameHandler.getTableName(tableName);
		Field[] fields = getColumnFields(clazz);
		String primaryName = getpriKey(fields);
		primaryName = nameHandler.getPrimaryName(primaryName);
		if (primaryName == null) {
			throw new RuntimeException(clazz.getName() + "未找到主键");
		}
		sql.append("delete from ");
		sql.append(tableName);
		sql.append(" ");
		Object primaryValue = null;
		for (Field field : fields) {
			String name = field.getName();
			Object value = null;
			try {
				value = PropertyUtils.getProperty(bean, name);
			} catch (Exception e) {
				throw new RuntimeException("获取属性值错误:", e);
			}
			Id id = field.getAnnotation(Id.class);
			if (id != null) {
				primaryValue = value;
			}
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" where ");
		sql.append(primaryName);
		sql.append(" = ?");
		params.add(primaryValue);
		return new SqlContext(sql, primaryName, params);
	}

	/**
	 * 构建查询条件
	 * 
	 * @param entity
	 * @param INameHandler
	 */
	public static SqlContext buildQueryCondition(Object bean,
			INameHandler nameHandler) {
		// 获取属性信息
		Class<?> clazz = bean.getClass();
		Field[] fields = getColumnFields(clazz);
		StringBuilder condition = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		int count = 0;
		for (Field field : fields) {
			String name = field.getName();
			Object value = null;
			try {
				value = PropertyUtils.getProperty(bean, name);
			} catch (Exception e) {
				throw new RuntimeException("获取属性值错误:", e);
			}
			if (value == null) {
				continue;
			}
			if (count > 0) {
				condition.append(" and ");
			}
			Column col = field.getAnnotation(Column.class);
			String columnName = col.value();
			columnName = nameHandler.getColumnName(columnName);
			if (StringUtils.isEmpty(columnName)) {
				continue;
			}
			condition.append(columnName);
			condition.append(" = ?");
			params.add(value);
			count++;
		}
		return new SqlContext(condition, null, params);
	}

	/**
	 * 构建查询sql
	 * 
	 * @param entity
	 * @param INameHandler
	 */
	public static SqlContext buildQuerySql(Object bean, INameHandler nameHandler) {
		// 获取属性信息
		Class<?> clazz = bean.getClass();
		Field[] fields = getColumnFields(clazz);
		StringBuilder querySql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		Tabel tab = clazz.getAnnotation(Tabel.class);
		if (tab == null) {
			return null;
		}
		String tableName = tab.value();
		tableName = nameHandler.getTableName(tableName);
		querySql.append("select *from ");
		querySql.append(tableName + " where ");
		for (Field field : fields) {
			String name = field.getName();
			Object value = null;
			try {
				value = PropertyUtils.getProperty(bean, name);
			} catch (Exception e) {
				throw new RuntimeException("获取属性值错误:", e);
			}
			if (value == null) {
				continue;
			}
			params.add(value);
		}
		SqlContext sqlContext = buildQueryCondition(bean, nameHandler);
		StringBuilder sql = sqlContext.getSql();
		sqlContext.setSql(querySql.append(sql));
		return sqlContext;
	}

}
