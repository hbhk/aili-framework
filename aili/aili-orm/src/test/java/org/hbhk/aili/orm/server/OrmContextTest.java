package org.hbhk.aili.orm.server;

import java.io.IOException;
import java.util.Set;

import org.hbhk.aili.orm.server.context.OrmContext;

public class OrmContextTest {
	public static void main(String[] args) throws IOException {
		OrmContext.getSql("asdasd");
		Set<String> keys = OrmContext.getOrmContext().keySet();
		for (String string : keys) {
			System.out.println(OrmContext.getOrmContext().get(string));

		}
	}
}
