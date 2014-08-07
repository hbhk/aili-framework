package org.hbhk.aili.nosql.server;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class UpdateDocumentApp {
	public static void printAllDocuments(DBCollection collection) {
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	public static void removeAllDocuments(DBCollection collection) {
		collection.remove(new BasicDBObject());
	}

	public static void insertDummyDocuments(DBCollection collection) {
		BasicDBObject document = new BasicDBObject();
		document.put("hosting", "hostA");
		document.put("type", "vps");
		document.put("clients", 1000);
		
		BasicDBObject document2 = new BasicDBObject();
		document2.put("hosting", "hostB");
		document2.put("type", "dedicated server");
		document2.put("clients", 100);
		
		BasicDBObject document3 = new BasicDBObject();
		document3.put("hosting", "hostC");
		document3.put("type", "vps");
		document3.put("clients", 900);
		
		collection.insert(document);
		collection.insert(document2);
		collection.insert(document3);
	}

	public static void main(String[] args) {
		try {
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("yourdb");
			DBCollection collection = db.getCollection("dummyColl");
			System.out.println("Testing 1...");
			insertDummyDocuments(collection);
			
			// find hosting = hostB, and update it with new document
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("hosting", "hostB");
			newDocument.put("type", "shared host");
			newDocument.put("clients", 111);
			collection.update(new BasicDBObject().append("hosting", "hostB"),
					newDocument);
			printAllDocuments(collection);
			removeAllDocuments(collection);
			System.out.println("Testing 2...");
			insertDummyDocuments(collection);
			BasicDBObject newDocument2 = new BasicDBObject().append("$inc",
					new BasicDBObject().append("clients", 99));
			collection.update(new BasicDBObject().append("hosting", "hostB"),
					newDocument2);
			printAllDocuments(collection);
			removeAllDocuments(collection);
			System.out.println("Testing 3...");
			insertDummyDocuments(collection);
			BasicDBObject newDocument3 = new BasicDBObject().append("$set",
					new BasicDBObject().append("type", "dedicated server"));
			collection.update(new BasicDBObject().append("hosting", "hostA"),
					newDocument3);
			printAllDocuments(collection);
			removeAllDocuments(collection);
			System.out.println("Testing 4...");
			insertDummyDocuments(collection);
			BasicDBObject updateQuery = new BasicDBObject().append("$set",
					new BasicDBObject().append("clients", "888"));
			collection.update(new BasicDBObject().append("type", "vps"),
					updateQuery, false, true);
			printAllDocuments(collection);
			removeAllDocuments(collection);
			System.out.println("Done");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
}