package org.hbhk.aili.mybatis.server;
import java.util.List;

import org.hbhk.aili.mybatis.server.support.ColumnInfo;
import org.hbhk.aili.mybatis.server.support.TableInfo;
import org.hbhk.aili.mybatis.server.support.TableInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class MapperTest {
	public static final Logger log = LoggerFactory.getLogger(MapperTest.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Test
	public void  query1(){
		StringBuilder sql = new StringBuilder();
		sql.append("select table_name,column_name,data_type from ");
		sql.append("information_schema.columns where table_schema = 'hbhk' and ");
		sql.append("table_name in ('t_aili_user')");
		List<TableInfo> tableInfos = jdbcTemplate.query(sql.toString(), new OneToManyRowMapperResultSetExtractor<TableInfo>(new TableInfoMapper()));
		System.out.println(tableInfos.size());
		for (TableInfo tableInfo : tableInfos) {
			 List<ColumnInfo> ss = tableInfo.getColumnList();
		}
	}
}

