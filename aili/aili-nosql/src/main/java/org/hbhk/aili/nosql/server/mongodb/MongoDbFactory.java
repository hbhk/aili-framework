package org.hbhk.aili.nosql.server.mongodb;

import org.springframework.dao.DataAccessException;

import com.mongodb.DB;
/**
 * 
 *  获取mongodb DB实例接口
 */
public interface MongoDbFactory {

    /**
     */
    DB getDb() throws DataAccessException;

    /**
     */
    DB getDb(String dbName) throws DataAccessException;
    
}
