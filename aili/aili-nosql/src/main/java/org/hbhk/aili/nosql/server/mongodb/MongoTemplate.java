package org.hbhk.aili.nosql.server.mongodb;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
/**
 *  提供操作mongodb模板类
 */
public class MongoTemplate {
    
    @SuppressWarnings("serial")
    private static final List<String> ITERABLE_CLASSES = new ArrayList<String>() {
        {
            add(List.class.getName());
            add(Collection.class.getName());
            add(Iterator.class.getName());
        }
    };
    private static final String ID = "_id";
    private MongoDbFactory mongoDbFactory;
    
    public void setMongoDbFactory(MongoDbFactory mongoDbFactory) {
        this.mongoDbFactory = mongoDbFactory;
    }

    public String getCollectionName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }
    /**
     * <p>从工厂获取mongo DB</p> 
     */
    public DB getDb() {
        return mongoDbFactory.getDb();
    }
    /**
     * <p>从工厂中获取指定连接的mongo DBCollection</p> 
     */
    private DBCollection getDbCollection(String collectionName) {
        return mongoDbFactory.getDb().getCollection(collectionName);
    }
    /**
     * <p>判断对象是否是一个集合</p> 
     */
    protected void ensureNotIterable(Object o) {
        if (null != o) {
            if (o.getClass().isArray() || ITERABLE_CLASSES.contains(o.getClass().getName())) {
                throw new IllegalArgumentException("Cannot use a collection here.");
            }
        }
    }
    /**
     * <p>给集合命名，名称为对象名称</p> 
     */
    String determineCollectionName(Class<?> entityClass) {

        if (entityClass == null) {
            throw new InvalidDataAccessApiUsageException(
                    "No class parameter provided, entity collection can't be determined!");
        }
        return entityClass.getName();
    }
    /**
     * <p>为一个集合实体命名</p> 
     */
    private <T> String determineEntityCollectionName(T obj) {
        if (null != obj) {
            return determineCollectionName(obj.getClass());
        }

        return null;
    }
    /**
     * <p>根据对象和对象名称命名对象id</p> 
     */
    public ObjectId insert(Object objectToSave) {
        ensureNotIterable(objectToSave);
        return insert(objectToSave, determineEntityCollectionName(objectToSave));
    }
    
    public ObjectId insert(Object objectToSave, String collectionName) {
        ensureNotIterable(objectToSave);
        DBCollection dbCollection = getDbCollection(collectionName);
        BasicDBObject dbDoc = convert(objectToSave);
        dbCollection.insert(dbDoc);
        return dbDoc.getObjectId(ID);
    }
    
    public List<ObjectId> insert(Collection<? extends Object> batchToSave, Class<?> entityClass) {
        return doInsertBatch(determineCollectionName(entityClass), batchToSave);
    }

    public List<ObjectId> insert(Collection<? extends Object> batchToSave, String collectionName) {
        return doInsertBatch(collectionName, batchToSave);
    }
    
    public void insertAll(Collection<? extends Object> objectsToSave) {
        doInsertAll(objectsToSave);
    }
    
    protected <T> void doInsertAll(Collection<? extends T> listToSave) {
        Map<String, List<T>> objs = new HashMap<String, List<T>>();

        for (T o : listToSave) {

            String collection = getCollectionName(o.getClass());

            List<T> objList = objs.get(collection);
            if (null == objList) {
                objList = new ArrayList<T>();
                objs.put(collection, objList);
            }
            objList.add(o);

        }

        Map<String,List<ObjectId>> res = new HashMap<String, List<ObjectId>>();
        for (Map.Entry<String, List<T>> entry : objs.entrySet()) {
            res.put(entry.getKey(), doInsertBatch(entry.getKey(), entry.getValue()));
        }
    }
    
    protected <T> List<ObjectId> doInsertBatch(String collectionName, Collection<? extends T> batchToSave) {

        List<DBObject> dbObjectList = new ArrayList<DBObject>();
        for (T o : batchToSave) {
            dbObjectList.add(convert(o));
        }
        return insertDBObjectList(collectionName, dbObjectList);
    }
    
    protected List<ObjectId> insertDBObjectList(final String collectionName, final List<DBObject> dbDocList) {
        if (dbDocList.isEmpty()) {
            return Collections.emptyList();
        }
        
        DBCollection dbCollection = getDbCollection(collectionName);
        dbCollection.insert(dbDocList);

        List<ObjectId> ids = new ArrayList<ObjectId>();
        for (DBObject dbo : dbDocList) {
            Object id = dbo.get(ID);
            if (id instanceof ObjectId) {
                ids.add((ObjectId) id);
            } else {
                // no id was generated
                ids.add(null);
            }
        }
        return ids;
    }
    
    public void save(Object objectToSave) {
        save(objectToSave, determineEntityCollectionName(objectToSave));
    }

    public void save(Object objectToSave, String collectionName) {
        doSave(collectionName, objectToSave);
    }

    protected <T> void doSave(String collectionName, T objectToSave) {

        BasicDBObject dbDoc = new BasicDBObject();
        
        convert(objectToSave);

        saveDBObject(collectionName, dbDoc);
    }
    
    protected Object saveDBObject(final String collectionName, final DBObject dbDoc) {
        DBCollection dbcollection = getDbCollection(collectionName);
        dbcollection.save(dbDoc);
        return dbDoc.get(ID);
    }
    
    public WriteResult upsert(Map<String,Object> query, Map<String,Object> update, Class<?> entityClass) {
        return doUpdate(determineCollectionName(entityClass), query, update, entityClass, true, false);
    }

    public WriteResult upsert(Map<String,Object> query, Map<String,Object> update, String collectionName) {
        return doUpdate(collectionName, query, update, null, true, false);
    }

    public WriteResult updateFirst(Map<String,Object> query, Map<String,Object> update, Class<?> entityClass) {
        return doUpdate(determineCollectionName(entityClass), query, update, entityClass, false, false);
    }

    public WriteResult updateFirst(final Map<String,Object> query, final Map<String,Object> update, final String collectionName) {
        return doUpdate(collectionName, query, update, null, false, false);
    }

    public WriteResult updateMulti(Map<String,Object> query, Map<String,Object> update, Class<?> entityClass) {
        return doUpdate(determineCollectionName(entityClass), query, update, entityClass, false, true);
    }

    public WriteResult updateMulti(final Map<String,Object> query, final Map<String,Object> update, String collectionName) {
        return doUpdate(collectionName, query, update, null, false, true);
    }

    protected WriteResult doUpdate(final String collectionName, final Map<String,Object> query, final Map<String,Object> update,
            final Class<?> entityClass, final boolean upsert, final boolean multi) {

        DBCollection dbcollection = getDbCollection(collectionName);
        
        DBObject queryObj = convert(query);
        
        DBObject updateObj = convert(update);
        
        return dbcollection.update(queryObj, updateObj, upsert, multi);
    }
    
    public void remove(Object object) {

        if (object == null) {
            return;
        }

        remove(object, determineEntityCollectionName(object));
    }
   /**
    * 
    * <p>删除指定连接</p> 
    */
    public void remove(Object object, String collection) {

        Assert.hasText(collection);

        if (object == null) {
            return;
        }

        doRemove(collection,object);
    }
    
    protected <T> void doRemove(final String collectionName, final Object object) {
        if (object == null) {
            throw new InvalidDataAccessApiUsageException("Query passed in to remove can't be null");
        }
        DBCollection dbcollection = getDbCollection(collectionName);
        
        DBObject dboq = convert(object);
        
        dbcollection.remove(dboq);
    }
    /**
     * <p>将对象转换成mongo BasicDBObject</p> 
     */
    private BasicDBObject convert(Object objectToSave) {
        BasicDBObject dbDoc = new BasicDBObject();
        Field[] fields = objectToSave.getClass().getDeclaredFields();
        PropertyDescriptor pd = null;
        for(int i=0;i<fields.length;i++) {
            try {
                pd = new PropertyDescriptor(fields[i].getName(),this.getClass());
                Method m = pd.getReadMethod();
                dbDoc.put(fields[i].getName(), m.invoke(this));
            } catch(Exception e) {
                continue;
            }
        }
        return dbDoc;
    }
    
}
