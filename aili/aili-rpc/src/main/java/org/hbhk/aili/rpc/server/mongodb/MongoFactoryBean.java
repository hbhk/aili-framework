package org.hbhk.aili.nosql.server.mongodb;

import java.util.Arrays;
import java.util.List;

import org.hbhk.aili.nosql.share.ex.CannotGetMongoDbConnectionException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
/**
 * mongo数据库连接配置
 */
public class MongoFactoryBean implements FactoryBean<Mongo>, InitializingBean, DisposableBean,
PersistenceExceptionTranslator {

    private Mongo mongo;

    private MongoOptions mongoOptions;
    private String host;
    private Integer port;
    private WriteConcern writeConcern;
    private List<ServerAddress> replicaSetSeeds;
    private List<ServerAddress> replicaPair;

    private PersistenceExceptionTranslator exceptionTranslator = new MongoExceptionTranslator();
    
    public void setMongoOptions(MongoOptionsFactoryBean mongoOptionsFactory) {
        this.mongoOptions = mongoOptionsFactory.getObject();
    }

    public void setMongoOptions(MongoOptions mongoOptions) {
        this.mongoOptions = mongoOptions;
    }

    public void setReplicaSetSeeds(ServerAddress[] replicaSetSeeds) {
        this.replicaSetSeeds = Arrays.asList(replicaSetSeeds);
    }

    public void setReplicaPair(ServerAddress[] replicaPair) {
        this.replicaPair = Arrays.asList(replicaPair);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Sets the {@link WriteConcern} to be configured for the {@link Mongo} instance to be created.
     * 
     * @param writeConcern
     */
    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    public void setExceptionTranslator(PersistenceExceptionTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    public Mongo getObject() {
        return mongo;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<? extends Mongo> getObjectType() {
        return Mongo.class;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.dao.support.PersistenceExceptionTranslator#translateExceptionIfPossible(java.lang.RuntimeException)
     */
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return exceptionTranslator.translateExceptionIfPossible(ex);
    }

    /* 
     * (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {

        Mongo mongo;
        ServerAddress defaultOptions = new ServerAddress();

        if (mongoOptions == null) {
            mongoOptions = new MongoOptions();
        }

        if (replicaPair != null) {
            if (replicaPair.size() < 2) {
                throw new CannotGetMongoDbConnectionException("A replica pair must have two server entries");
            }
            mongo = new Mongo(replicaPair.get(0), replicaPair.get(1), mongoOptions);
        } else if (replicaSetSeeds != null) {
            mongo = new Mongo(replicaSetSeeds, mongoOptions);
        } else {
            String mongoHost = host != null ? host : defaultOptions.getHost();
            mongo = port != null ? new Mongo(new ServerAddress(mongoHost, port), mongoOptions) : new Mongo(mongoHost,
                    mongoOptions);
        }

        if (writeConcern != null) {
            mongo.setWriteConcern(writeConcern);
        }

        this.mongo = mongo;
    }

    /* 
     * (non-Javadoc)
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {
        this.mongo.close();
    }
}
