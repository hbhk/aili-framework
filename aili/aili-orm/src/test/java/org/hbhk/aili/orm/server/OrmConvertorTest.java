package org.hbhk.aili.orm.server;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.hbhk.aili.orm.server.convertor.OrmConvertor;
import org.hbhk.aili.orm.share.model.Delete;
import org.hbhk.aili.orm.share.model.Orm;

public class OrmConvertorTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		Orm o = new Orm();
		Delete d = new Delete();
		d.setSql("sql111111111");
		d.setId("id");
		d.setClazz("class");
		List<Delete> ds = new ArrayList<Delete>();
		ds.add(d);
		OrmConvertor c = new OrmConvertor();
		o.setDelete(ds);
		String str = c.fromMessage(o);
		Orm orm = c.toMessage(str);
		System.out.println(str);
		System.out.println(orm.getDelete().get(0).getSql());
	}

}
