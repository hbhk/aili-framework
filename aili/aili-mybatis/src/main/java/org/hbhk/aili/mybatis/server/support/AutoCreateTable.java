package org.hbhk.aili.mybatis.server.support;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.share.util.AnnotationScanningUtil;
import org.hbhk.aili.mybatis.server.annotation.Column;
import org.hbhk.aili.mybatis.server.annotation.Table;
import org.hbhk.aili.mybatis.server.handler.INameHandler;
import org.hbhk.aili.mybatis.share.util.SqlUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class AutoCreateTable implements InitializingBean {

	public static Log logger = LogFactory.getLog(AutoCreateTable.class);
	@Autowired
	private INameHandler nameHandler;

	private JdbcTemplate jdbcTemplate;

	private static Map<String, TableInfo> tableCache = new ConcurrentHashMap<String, TableInfo>();

	private static Map<String, TableInfo> dbTableCache = new ConcurrentHashMap<String, TableInfo>();

	/**
	 * 多个包用,分割
	 */
	@Value("${auto.scan.table.packages}")
	private String autoTablePath;
	@Value("${scann.package}")
	private static String scannPackage;

	@Value("${table.schema}")
	private static String tableSchema;

	private static List<String> tableNames = new ArrayList<String>();

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void createTable(Class<?> cls, String tableName) {
		StringBuffer sb = new StringBuffer("");
		Field[] fields = SqlUtil.getColumnFields(cls);
		sb.append("create table " + tableName + "(\n");
		String pk = SqlUtil.getpriKey(fields);
		pk = nameHandler.getPrimaryName(pk);
		sb.append(pk + " integer unsigned not null auto_increment,\n");
		List<String> columnNames = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Column col = field.getAnnotation(Column.class);
			if (col == null) {
				continue;
			}
			String columnName = col.value();
			columnName = nameHandler.getColumnName(columnName);
			if (StringUtils.isEmpty(columnName)) {
				continue;
			}
			if (columnNames.contains(columnName)) {
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
		sb.append(") engine=innodb default charset=utf8;");
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
		initTableMap();
		if (StringUtils.isNotEmpty(autoTablePath)) {
			getAllTableNames();
			initTableMap();
			String[] autoTablePaths = autoTablePath.split(",");
			List<Class<?>> classes = AnnotationScanningUtil
					.getAnnotatedClasses(Table.class, autoTablePaths);
			if (classes != null) {
				for (Class<?> cls : classes) {
					Table tab = cls.getAnnotation(Table.class);
					if (tab == null) {
						continue;
					}
					String tableName = tab.value();
					tableName = nameHandler.getTableName(tableName);
					if (!exits(tableName)) {
						createTable(cls, tableName);
					} else {
						// select TABLE_NAME,COLUMN_NAME,DATA_TYPE from
						// information_schema.COLUMNS where table_name =
						// 't_aili_uSer' and table_schema = 'hbhk';
						// 比较列  没有添加该列
					}
				}
			}
		}
	}

	public List<String> getAllTableNames() {
		try {
			if (tableNames != null && tableNames.size() > 0) {
				return tableNames;
			}
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			// 表名列表
			ResultSet rest = dbmd.getTables(null, null, "%", null);
			// 输出 table_name
			while (rest.next()) {
				tableNames.add(rest.getString("TABLE_NAME").toLowerCase());
			}
			// 根据表名查询
		} catch (Exception e) {
			logger.error("查询数据库所有表出错：", e);
			throw new RuntimeException("查询数据库所有表出错：", e);
		}

		return tableNames;
	}

	public void initTableMap() throws ClassNotFoundException {

		if (StringUtils.isEmpty(scannPackage)) {
			scannPackage = "org.hbhk.**.model";
		}
		String[] scannPackages = scannPackage.split(",");
		List<Class<?>> tables = AnnotationScanningUtil.getAnnotatedClasses(
				Table.class, scannPackages);
		List<String> tabNames = new ArrayList<String>();
		if (tables != null) {
			for (Class<?> cls : tables) {
				Table tab = cls.getAnnotation(Table.class);
				String name = tab.value().toLowerCase();
				tabNames.add(name);
				TableInfo tableInfo = new TableInfo();
				tableInfo.setName(name);
				// 获取列
				Field[] fields = SqlUtil.getColumnFields(cls);
				List<ColumnInfo> columnList = new ArrayList<ColumnInfo>();
				List<String> columnStrs = new ArrayList<String>();
				for (Field field : fields) {
					ColumnInfo columnInfo = new ColumnInfo();
					Column col = field.getAnnotation(Column.class);
					String fname = col.value().toLowerCase();
					String type = col.dbType();
					int length = col.len();
					columnInfo.setDataType(type);
					columnInfo.setLength(length);
					columnInfo.setName(fname);
					columnList.add(columnInfo);
					columnStrs.add(fname);
				}
				tableInfo.setColumnList(columnList);
				tableInfo.setColumnStrs(columnStrs);
				tableCache.put(name, tableInfo);
			}
			StringBuilder sql = new StringBuilder();
			sql.append("select table_name,column_name,data_type from ");
			sql.append("information_schema.columns where table_schema = "
					+ tableSchema + " ");
			sql.append("table_name in (");
			for (int i = 0; i < tabNames.size(); i++) {
				String str = tabNames.get(i);
				if ((i + 1) == tabNames.size()) {
					sql.append(str + "");
				} else {
					sql.append(str + ",");
				}
			}
			sql.append(")");

			List<TableInfo> tableInfos = jdbcTemplate.query(sql.toString(),
					new TableInfoMapper());
			List<String> tabs = new ArrayList<String>();
			TableInfo tableObj = new TableInfo();
			for (TableInfo tableInfo : tableInfos) {
				String tabName = tableInfo.getName();
				if (!tabs.contains(tabName)) {
					tableObj = tableInfo;
					dbTableCache.put(tabName, tableObj);
				} else {
					tableObj.getColumnList().addAll(tableInfo.getColumnList());
					tableObj.getColumnStrs().addAll(tableInfo.getColumnStrs());
				}
			}
		}

	}
}
