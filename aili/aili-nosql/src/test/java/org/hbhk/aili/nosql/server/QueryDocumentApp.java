package org.hbhk.aili.nosql.server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class QueryDocumentApp {

	Mongo mongo = null;
	private DBCollection collection;

	@Before
	public void before() throws UnknownHostException {
		mongo = new Mongo();
		DB db = mongo.getDB("hbhk");
		Set<String> collections = db.getCollectionNames();
		for (String collectionName : collections) {
			System.out.println(collectionName);
		}
		collection = db.getCollection("hbhk1");
	}

	@Test
	public void insert() {
		for (int i = 1; i <= 10; i++) {
			collection.insert(new BasicDBObject().append("number", i));

		}
	}
	@Test
	public void findOne() {
		// 获得数据库中的第一个document:
		DBObject doc = collection.findOne();
		System.out.println(doc);
	}
	@Test
	public void documents() {
		// 获得document的集合
		DBCursor cursor = collection.find().skip(2);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}
	
	@Test
	public void documentsPage() {
		// 获得document的集合
		DBCursor cursor = collection.find().skip(2).limit(5);
		int count = 0;
		if(cursor.hasNext()){
			count = 10;
		}else{
			 count = cursor.count();
		}
		
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}
	@Test
	public void finddocuments() {
		// 获取指定的document
		BasicDBObject query = new BasicDBObject();
		query.put("number", 5);
		DBCursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}
	@Test
	public void in() {
		BasicDBObject query =new BasicDBObject();
		List<Integer> list =new ArrayList<Integer>();
		list.add(9);
		list.add(10);
		query.put("number", new BasicDBObject("$in", list));
		DBCursor cursor = collection.find(query);
		while(cursor.hasNext()){
		System.out.println(cursor.next());
		}
	}

	// 大于 小于
	@Test
	public void gtTolt() {
		BasicDBObject query = new BasicDBObject();
		query.put("number", new BasicDBObject("$gt", 5).append("$lt", 8));
		DBCursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	// 不等于
	public void ne() {
		BasicDBObject query5 = new BasicDBObject();
		query5.put("number", new BasicDBObject("$ne", 8));
		DBCursor cursor6 = collection.find(query5);
		while (cursor6.hasNext()) {
			System.out.println(cursor6.next());
		}
	}

	public void remove() {
		// 1) 删除第一个document
		DBObject doc = collection.findOne();
		collection.remove(doc);

		// 2) 删除指定的document
		// 比如删除number=2的document,如下方法：
		BasicDBObject document = new BasicDBObject();
		document.put("number", 2);
		collection.remove(document);
		// 要注意的是，如下的方法将只会删除number=3的document。
		// BasicDBObject document =new BasicDBObject();
		// document.put("number", 2);
		// document.put("number", 3);
		// collection.remove(document);

		// 3) 使用in 操作符号指定删除document
		// 下面的例子将同时删除number=4和number=5的document，使用的是in操作符
		BasicDBObject query2 = new BasicDBObject();
		List<Integer> list = new ArrayList<Integer>();
		list.add(4);
		list.add(5);
		query2.put("number", new BasicDBObject("$in", list));
		collection.remove(query2);

		// 4) 使用“$gt”删除大于某个值的document
		BasicDBObject query = new BasicDBObject();
		query.put("number", new BasicDBObject("$gt", 9));
		collection.remove(query);

		// 5) 删除所有的document
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			collection.remove(cursor.next());
		}
	}
}
