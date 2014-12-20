package org.hbhk.aili.mybatis.server.support;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.core.server.annotation.AnnotationScanning;
import org.hbhk.aili.mybatis.server.annotation.Table;
import org.hbhk.aili.mybatis.server.interceptor.AiliMybatisInterceptor;
import org.hbhk.aili.mybatis.share.model.BaseInfo;
import org.hbhk.aili.mybatis.share.model.TableInfo;
import org.hbhk.aili.mybatis.share.util.SqlUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

public class MybatisSqlTemplate<T extends BaseInfo> implements InitializingBean {

	private Class<?> fxType = AiliMybatisInterceptor.fxType;
	/**
	 * 多个包用,分割
	 */
	@Value("${auto.scan.table.packages}")
	private String autoTablePath;

	private static Map<String, TableInfo> tabs = new HashMap<String, TableInfo>();

	public String insert(T obj) {

		return "";
	}

	public String update(T obj) {

		return "";
	}

	public String getById(Map<String, Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select *from ");
		sql.append(getTableName());
		sql.append(" where id = #{id}");
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

					tabs.put(tab.getName(), tableInfo);

				}
			}
		}
	}

	private String getTableName() {
		String tab = fxType.getAnnotation(Table.class).value();
		return tab;
	}
}
