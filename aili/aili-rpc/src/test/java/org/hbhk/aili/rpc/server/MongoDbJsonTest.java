package org.hbhk.aili.nosql.server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hbhk.aili.nosql.server.pojo.JsonData;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDbJsonTest {

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
		collection = db.getCollection("hbhk-c");
	}

	@Test
	public void testBasicDBObject() {
		BasicDBObject document = new BasicDBObject();
		document.put("database", "mkyongDB");
		document.put("table", "hosting");
		BasicDBObject documentDetail = new BasicDBObject();
		documentDetail.put("records", "99");
		documentDetail.put("index", "vps_index1");
		documentDetail.put("active", "true");
		document.put("detail", documentDetail);
		collection.insert(document);
	}

	@Test
	public void testBasicDBObjectBuilder() {
		BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start()
				.add("database", "mkyongDB").add("table", "hosting");
		BasicDBObjectBuilder documentBuilderDetail = BasicDBObjectBuilder
				.start().add("records", "99").add("index", "vps_index1")
				.add("active", "true");
		documentBuilder.add("detail", documentBuilderDetail.get());
		collection.insert(documentBuilder.get());

	}
	
	@Test
	public void testMap() {
		Map<String,Object> documentMap =new HashMap<String,Object>();
		documentMap.put("database", "mkyongDB");
		documentMap.put("table", "hosting");
		Map<String,Object> documentMapDetail =new HashMap<String,Object>();
		documentMapDetail.put("records", "99");
		documentMapDetail.put("index", "vps_index1");
		documentMapDetail.put("active", "true");
		documentMap.put("detail", documentMapDetail);
		collection.insert(new BasicDBObject(documentMap));

	}
	
	
	@Test
	public void testJson() {
		try {
			List<JsonData> jsons = new ArrayList<JsonData>();
			for (int i = 0; i <3; i++) {
				jsons.add(new JsonData());
			}
			String json  = JSON.toJSONString(new JsonData());
			DBObject dbObject =(DBObject)com.mongodb.util.JSON.parse(json);
			//BasicBSONList vv = (BasicBSONList)com.mongodb.util.JSON.parse(json);
			collection.insert(dbObject);
			q();
		} catch (Exception e) {
			e.printStackTrace();
		}
	

	}
	
	public void q(){
		DBCursor cursorDocJSON = collection.find();
		while(cursorDocJSON.hasNext()){
		System.out.println(cursorDocJSON.next());
		}
	}
}
