package org.hbhk.aili.nosql.server.mongodb;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DBCollection;
/**
 * 
 * 提供查询mongodb数据库mongodb工厂
 */
public class MongoDaoSupport {
    
    private MongoDbFactory mongoDbFactory;
    
    @Autowired(required = false)
    public void setMongoDbFactory(MongoDbFactory mongoDbFactory) {
        this.mongoDbFactory = mongoDbFactory;
    }
    
    public DBCollection getDbCollection(String collectionName) {
        return mongoDbFactory.getDb().getCollection(collectionName);
    }
    
}
