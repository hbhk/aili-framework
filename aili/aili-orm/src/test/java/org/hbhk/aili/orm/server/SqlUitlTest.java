package org.hbhk.aili.orm.server;

import org.hbhk.aili.orm.server.handler.DefaultNameHandler;
import org.hbhk.aili.orm.share.util.SqlUtil;

public final class SqlUitlTest {
	public static void main(String[] args) {
		SqlModelTest del = new SqlModelTest();
		del.setId("id");
		del.setQqq("dff");
		del.setSss("sss");
		String ss = SqlUtil.buildInsertSql(del, new DefaultNameHandler()).getSql().toString();
		String q = SqlUtil.buildQueryCondition(del, new DefaultNameHandler()).getSql().toString();
		String u = SqlUtil.buildUpdateSql(del, new DefaultNameHandler()).getSql().toString();
		String d = SqlUtil.buildDeleteSql(del, new DefaultNameHandler()).getSql().toString();
		String qs = SqlUtil.buildQuerySql(del, new DefaultNameHandler()).getSql().toString();
		System.out.println(ss);
		
		System.out.println(q);
		
		System.out.println(u);
		
		System.out.println(d);
		
		System.out.println(qs);
	}
}
