package org.hbhk.aili.mybatis.server.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public class TableInfoMapper implements RowMapper<TableInfo> {
	private List<String> tabs = new ArrayList<String>();
	private TableInfo tabObj = new TableInfo();

	@Override
	public TableInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// table_name,column_name,data_type
		TableInfo tab = new TableInfo();
		String name = rs.getString("table_name").toLowerCase();
		tab.setName(name);
		List<ColumnInfo> cols = new ArrayList<ColumnInfo>();
		ColumnInfo col = new ColumnInfo();
		String colName = rs.getString("column_name").toLowerCase();
		String type = rs.getString("data_type");
		col.setName(colName);
		col.setDataType(type);
		cols.add(col);
		col.setName(name);
		List<String> colStrs = new ArrayList<String>();
		colStrs.add(colName);
		tab.setColumnList(cols);
		tab.setColumnStrs(colStrs);
		return tab;
	}

}
