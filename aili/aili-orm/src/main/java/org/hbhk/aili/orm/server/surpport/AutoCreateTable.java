package org.hbhk.aili.orm.server.surpport;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.share.util.AnnotationScanningUtil;
import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.server.handler.INameHandler;
import org.hbhk.aili.orm.share.util.SqlUtil;
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

	/**
	 * 多个包用,分割
	 */
	@Value("${auto.scan.table.packages}")
	private String autoTablePath;

	private List<String> tableNames = new ArrayList<String>();

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void createTable(Class<?> cls, String tableName) {
		StringBuffer sb = new StringBuffer("");
		Field[] fields = SqlUtil.getColumnFields(cls);
		sb.append("CREATE TABLE " + tableName + "(\n");
		String pk = SqlUtil.getpriKey(fields);
		pk = nameHandler.getPrimaryName(pk);
		sb.append(pk + " INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,\n");
		List<String> columnNames = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Column col = field.getAnnotation(Column.class);
			if(col == null){
				continue;
			}
			String columnName = col.value();
			columnName = nameHandler.getColumnName(columnName);
			if(StringUtils.isEmpty(columnName)){
				continue;
			}
			if(columnNames.contains(columnName)){
				continue;
			}
			columnNames.add(columnName);
			if (columnName != null && !columnName.equals(pk)) {
				String dbtype = col.dbType();
				// 暂时解决现有项目的兼容 TODO
				if (dbtype.equals("varchar")) {
					dbtype = JavaType.getDbType(field.getType());
				} else {
					dbtype = dbtype + "(" + col.len() + ")";
				}
				sb.append(columnName + " " + dbtype + ",\n");
			}
		}
		sb.append("primary key (" + pk + ")\n");
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		try {
			logger.debug("自动创建表语句：" + sb.toString());
			jdbcTemplate.update(sb.toString());
		} catch (Exception e) {
			logger.error("自动创建表语句时错误", e);
			throw new RuntimeException("自动创建表语句时错误", e);
		}
	}

	private boolean exits(String tableName) {
		return tableNames.contains(tableName.toLowerCase());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isNotEmpty(autoTablePath)) {
			getAllTableNames();
			String[] autoTablePaths =  autoTablePath.split(",");
			List<Class<?>> classes = AnnotationScanningUtil
					.getAnnotatedClasses(Tabel.class, autoTablePaths);
			if (CollectionUtils.isNotEmpty(classes)) {
				for (Class<?> cls : classes) {
					Tabel tab = cls.getAnnotation(Tabel.class);
					if (tab == null) {
						continue;
					}
					String tableName = tab.value();
					tableName = nameHandler.getTableName(tableName);
					if (!exits(tableName)) {
						createTable(cls, tableName);
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
			logger.error("查询数据库所有表出错：", e);
			throw new RuntimeException("查询数据库所有表出错：", e);
		}

		return tableNames;
	}

}
