package org.hbhk.aili.nosql.server.mongodb;

import java.net.UnknownHostException;

import org.hbhk.aili.nosql.share.pojo.UserCredentials;
import org.hbhk.aili.nosql.share.util.MongoDbUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.mongodb.WriteConcern;
public class MongoDbFactoryBean implements DisposableBean, MongoDbFactory {
    //mongo对象
    private final Mongo mongo;
    //数据库名称
    private final String databaseName;
    //是否创建了mongo实例
    private final boolean mongoInstanceCreated;
    //登录mongo 数据库凭证
    private final UserCredentials credentials;
    private WriteConcern writeConcern;
    
    public MongoDbFactoryBean(MongoFactoryBean mongoFactory, String databaseName) {
        this(mongoFactory.getObject(), databaseName, new UserCredentials(), false);
    }
    
    public MongoDbFactoryBean(MongoFactoryBean mongoFactory, String databaseName, UserCredentials credentials) {
        this(mongoFactory.getObject(), databaseName, credentials, false);
    }

    /**
     * Create an instance of {@link MongoDbFactoryBean} given the {@link Mongo} instance and database name.
     * 
     * @param mongo Mongo instance, must not be {@literal null}.
     * @param databaseName database name, not be {@literal null}.
     */
    public MongoDbFactoryBean(Mongo mongo, String databaseName) {
        this(mongo, databaseName, new UserCredentials(), false);
    }

    /**
     * Create an instance of SimpleMongoDbFactory given the Mongo instance, database name, and username/password
     * 
     * @param mongo Mongo instance, must not be {@literal null}.
     * @param databaseName Database name, must not be {@literal null}.
     * @param credentials username and password.
     */
    public MongoDbFactoryBean(Mongo mongo, String databaseName, UserCredentials credentials) {
        this(mongo, databaseName, credentials, false);
    }

    /**
     * Creates a new {@link MongoDbFactoryBean} instance from the given {@link MongoURI}.
     * 
     * @param uri must not be {@literal null}.
     * @throws MongoException
     * @throws UnknownHostException
     * @see MongoURI
     */
    public MongoDbFactoryBean(MongoURI uri) throws MongoException, UnknownHostException {
        this(new Mongo(uri), uri.getDatabase(), new UserCredentials(uri.getUsername(), parseChars(uri.getPassword())), true);
    }

    private MongoDbFactoryBean(Mongo mongo, String databaseName, UserCredentials credentials,
            boolean mongoInstanceCreated) {

        Assert.notNull(mongo, "Mongo must not be null");
        Assert.hasText(databaseName, "Database name must not be empty");
        Assert.isTrue(databaseName.matches("[\\w-]+"),
                "Database name must only contain letters, numbers, underscores and dashes!");

        this.mongo = mongo;
        this.databaseName = databaseName;
        this.mongoInstanceCreated = mongoInstanceCreated;
        this.credentials = credentials == null ? new UserCredentials() : credentials;
    }

    /**
     * Configures the {@link WriteConcern} to be used on the {@link DB} instance being created.
     * 
     * @param writeConcern the writeConcern to set
     */
    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.mongodb.MongoDbFactory#getDb()
     */
    public DB getDb() throws DataAccessException {
        return getDb(databaseName);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.mongodb.MongoDbFactory#getDb(java.lang.String)
     */
    public DB getDb(String dbName) throws DataAccessException {

        Assert.hasText(dbName, "Database name must not be empty.");

        String username = credentials.getUsername();
        String password = credentials.getPassword();

        DB db = MongoDbUtils.getDB(mongo, dbName, username, password == null ? null : password.toCharArray());

        if (writeConcern != null) {
            db.setWriteConcern(writeConcern);
        }

        return db;
    }

    /**
     * Clean up the Mongo instance if it was created by the factory itself.
     * 
     * @see DisposableBean#destroy()
     */
    public void destroy() throws Exception {
        if (mongoInstanceCreated) {
            mongo.close();
        }
    }

    private static String parseChars(char[] chars) {
        return chars == null ? null : String.valueOf(chars);
    }

}
