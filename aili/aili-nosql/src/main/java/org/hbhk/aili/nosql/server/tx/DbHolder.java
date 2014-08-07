package org.hbhk.aili.nosql.server.tx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.transaction.support.ResourceHolderSupport;
import org.springframework.util.Assert;

import com.mongodb.DB;
/**
 * 
 * 提供事务支持
 */
public class DbHolder extends ResourceHolderSupport {
    private static final Object DEFAULT_KEY = new Object();
    //存储mongo数据库连接配置
    private final Map<Object, DB> dbMap = new ConcurrentHashMap<Object, DB>();

    public DbHolder(DB db) {
        addDB(db);
    }
    
    public DbHolder(Object key, DB db) {
        addDB(key, db);
    }

    public DB getDB() {
        return getDB(DEFAULT_KEY);
    }

    public DB getDB(Object key) {
        return this.dbMap.get(key);
    }

    public DB getAnyDB() {
        if (!this.dbMap.isEmpty()) {
            return this.dbMap.values().iterator().next();
        }
        return null;
    }
    /**
     * 添加一个mongo配置
     */
    public void addDB(DB session) {
        addDB(DEFAULT_KEY, session);
    }
   /**
    * <p>自定义key的mongo配置</p> 
    */
    public void addDB(Object key, DB session) {
        Assert.notNull(key, "Key must not be null");
        Assert.notNull(session, "DB must not be null");
        this.dbMap.put(key, session);
    }
    /**
     * <p>删除指定mongo</p> 
     */
    public DB removeDB(Object key) {
        return this.dbMap.remove(key);
    }
    /**
     * <p>判断 mongo 是否存在</p> 
     */
    public boolean containsDB(DB session) {
        return this.dbMap.containsValue(session);
    }
   /**
    * <p>判断mongo配置是否有mongo配置</p> 
    */
    public boolean isEmpty() {
        return this.dbMap.isEmpty();
    }

    public boolean doesNotHoldNonDefaultDB() {
        synchronized (this.dbMap) {
            return this.dbMap.isEmpty() || (this.dbMap.size() == 1 && this.dbMap.containsKey(DEFAULT_KEY));
        }
    }

}
