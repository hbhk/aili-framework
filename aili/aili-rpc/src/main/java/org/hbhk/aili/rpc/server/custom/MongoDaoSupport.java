package org.hbhk.aili.nosql.server.custom;

import java.util.List;
import java.util.Map;

import org.hbhk.aili.nosql.share.pojo.DBBaseInfo;
import org.hbhk.aili.nosql.share.util.CursorToJsonUtil;
import org.hbhk.aili.nosql.share.util.PaginationUtil;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoDaoSupport {

	private MongoDBConfig dbConfig;

	/**
	 * @Description:分页查询 建议使用这种分页
	 * @author 何波
	 * @param collection
	 * @param limit
	 * @param id
	 * @param queryID
	 * @return DBCursor
	 * @throws
	 * @date 2014年4月24日 下午4:28:43
	 */
	public DBCursor getDBCursor(String name, int limit, String id,
			String queryID) {
		DBObject object = new BasicDBObject();
		object.put(id, new BasicDBObject("$gt", queryID));
		DBCursor cursor = getDBCollection(name).find(object)
				.sort(new BasicDBObject("id", 1)).limit(limit);
		return cursor;
	}

	public <T> List<T> getDBCursor(String name, int limit, String id,
			String queryID, Class<? extends DBBaseInfo> clz) {
		DBObject object = new BasicDBObject();
		object.put(id, new BasicDBObject("$gt", queryID));
		DBCursor cursor = getDBCollection(name).find(object)
				.sort(new BasicDBObject("id", 1)).limit(limit);
		return CursorToJsonUtil.getCursorToObj(cursor, clz);
	}

	public DBCursor getDBCursor(String name, int limit, int pageNo) {
		int startIndex = PaginationUtil.getStartIndex(pageNo, limit);
		DBCursor cursor = getDBCollection(name).find().skip(startIndex)
				.limit(limit);
		return cursor;
	}

	public <T> List<T> getDBCursor(String name, int limit, int pageNo,
			Class<? extends DBBaseInfo> clz) {
		int startIndex = PaginationUtil.getStartIndex(pageNo, limit);
		DBCursor cursor = getDBCollection(name).find().skip(startIndex)
				.limit(limit);
		return CursorToJsonUtil.getCursorToObj(cursor, clz);
	}

	public void insert(Map<String, Object> docMap, String name) {
		getDBCollection(name).insert(new BasicDBObject(docMap));
	}

	public void insert(List<DBObject> doclist, String name) {
		getDBCollection(name).insert(doclist);
	}

	public void insert(String docJson, String name) {
		DBObject dbObject = (DBObject) JSON.parse(docJson);
		getDBCollection(name).insert(dbObject);
	}

	public void insert(BasicDBObject doc, String name) {
		getDBCollection(name).insert(doc);
	}

	public void insert(BasicDBObjectBuilder docBuilder, String name) {
		getDBCollection(name).insert(docBuilder.get());
	}

	public DBCursor queryIn(List<String> list, String q, String name) {
		BasicDBObject query = new BasicDBObject();
		query.put(q, new BasicDBObject("$in", list));
		return getDBCollection(name).find(query);
	}

	public DBCursor queryGt(Integer num, String q, String name) {
		BasicDBObject query = new BasicDBObject();
		query.put(q, new BasicDBObject("$gt", num));
		return getDBCollection(name).find(query);
	}

	public DBCursor queryLt(Integer num, String q, String name) {
		BasicDBObject query = new BasicDBObject();
		query.put(q, new BasicDBObject("$lt", num));
		return getDBCollection(name).find(query);
	}

	public DBCursor queryGtToLt(Integer gt, Integer lt, String q, String name) {
		BasicDBObject query = new BasicDBObject();
		query.put(q, new BasicDBObject("$gt", gt).append("$lt", lt));
		return getDBCollection(name).find(query);
	}

	public DBCollection getDBCollection(String name) {
		return dbConfig.getDBCollection(name);
	}

	public MongoDBConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(MongoDBConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public DB getDb() {
		return dbConfig.getDb();
	}

	public DB getDb(String database) {
		return dbConfig.getDb(database);
	}

}
