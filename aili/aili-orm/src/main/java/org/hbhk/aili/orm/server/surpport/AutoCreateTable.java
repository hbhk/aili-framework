package org.hbhk.aili.orm.server.surpport;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.server.annotation.AnnotationScanning;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.server.handler.INameHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AutoCreateTable implements InitializingBean {

	public static Log logger = LogFactory.getLog(AutoCreateTable.class);
	@Autowired
	private INameHandler nameHandler;

	private JdbcTemplate jdbcTemplate;

	@Value("${autoScanTablePackage}")
	private String autoTablePath;

	private List<String> tableNames = new ArrayList<String>();

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void createTable(Class<?> cls, String tableName) {
		StringBuffer sb = new StringBuffer("");
		sb.append("CREATE TABLE " + tableName + "(\n");
		String prikey = nameHandler.getPrimaryName(cls);
		sb.append(prikey + " varchar(255),\n");
		List<Field> fields = new ArrayList<Field>();
		Field field[] = getParentClassFields(fields, cls).toArray(
				new Field[] {});
		for (int i = 0; i < field.length; i++) {
			Field f = field[i];
			String columnName = nameHandler.getColumnName(cls, f.getName());
			if (columnName != null && !columnName.equals(prikey)) {
				String dbtype = JavaType.getDbType(f.getType());
				sb.append(columnName + " " + dbtype + ",\n");
			}
		}
		sb.append("primary key (" + prikey + ")\n");
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		try {
			logger.info("自动创建表语句：" + sb.toString());
			jdbcTemplate.update(sb.toString());
		} catch (Exception e) {
			logger.error("自动创建表语句时错误", e);
			logger.error("自动创建表语句时错误：" + sb.toString());
		}
	}

	private List<Field> getParentClassFields(List<Field> fields, Class<?> clazz) {
		Field[] selffields = clazz.getDeclaredFields();
		fields.addAll(Arrays.asList(selffields));
		if (clazz.getSuperclass() == null) {
			return fields;
		}
		getParentClassFields(fields, clazz.getSuperclass());
		return fields;
	}

	private boolean exits(String tableName) {
		return tableNames.contains(tableName.toLowerCase());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isNotEmpty(autoTablePath)) {
			getAllTableNames();
			List<Class<?>> classes = AnnotationScanning.getInstance()
					.getAnnotatedClasses(Tabel.class, autoTablePath);
			if (CollectionUtils.isNotEmpty(classes)) {
				for (Class<?> class1 : classes) {
					String tableName = nameHandler.getTableName(class1);
					if (!exits(tableName)) {
						createTable(class1, tableName);
					}
				}
			}
		}
	}

	public List<String> getAllTableNames() {
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			// 表名列表
			ResultSet rest = dbmd.getTables(null, null, "%", null);
			// 输出 table_name
			while (rest.next()) {
				tableNames.add(rest.getString("TABLE_NAME").toLowerCase());
			}
		} catch (Exception e) {
			logger.error("查询表出错：", e);
		}

		return tableNames;
	}

}
