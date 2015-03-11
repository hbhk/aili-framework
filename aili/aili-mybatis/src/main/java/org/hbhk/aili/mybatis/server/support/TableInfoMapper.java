package org.hbhk.aili.mybatis.server.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class TableInfoMapper implements RowMapper<TableInfo> {
	
	private Map<String, TableInfo>  mappers = new HashMap<String, TableInfo>();
	 
	@Override
	public TableInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// table_name,column_name,data_type
		TableInfo tab = new TableInfo();
		String name = rs.getString("table_name").toLowerCase();
		List<ColumnInfo> cols = new ArrayList<ColumnInfo>();
		ColumnInfo col = new ColumnInfo();
		String colName = rs.getString("column_name").toLowerCase();
		String type = rs.getString("data_type");
		col.setName(colName);
		col.setDataType(type);
		cols.add(col);
		col.setName(colName);
		List<String> colStrs = new ArrayList<String>();
		colStrs.add(colName);
		if(mappers.containsKey(name)){
			tab = mappers.get(name);
			tab.getColumnList().addAll(cols);
			tab.getColumnStrs().addAll(colStrs);
		}else{
			tab.setName(name);
			tab.setColumnList(cols);
			tab.setColumnStrs(colStrs);
			mappers.put(name, tab);
		}
		return tab;
	
	}

}
