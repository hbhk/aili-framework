package org.hbhk.aili.nosql.server.custom;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDBConfig {

	private Mongo mongo;
	private String host = "127.0.0.1";
	private int port = 27017;
	private String database;

	public MongoDBConfig() throws UnknownHostException {
		mongo = new Mongo(host, port);
	}

	
	public MongoDBConfig(String host, int port) throws UnknownHostException {
		mongo = new Mongo(host, port);
	}


	public DB getDb(String dbname) {
		return mongo.getDB(dbname);
	}

	public DBCollection getDBCollection(String name) {
		return getDb().getCollection(name);
	}

	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public DB getDb() {
		return mongo.getDB(database);
	}


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	
}
