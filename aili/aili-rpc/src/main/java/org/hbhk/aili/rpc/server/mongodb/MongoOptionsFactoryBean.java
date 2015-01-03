package org.hbhk.aili.nosql.server.mongodb;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.mongodb.MongoOptions;
/**
 * 初始化mongodb配置信息
 */

public class MongoOptionsFactoryBean implements FactoryBean<MongoOptions>, InitializingBean {

    private static final MongoOptions MONGO_OPTIONS = new MongoOptions();
    /**
     * number of connections allowed per host will block if run out
     */
    private int connectionsPerHost = MONGO_OPTIONS.connectionsPerHost;

    /**
     * multiplier for connectionsPerHost for # of threads that can block if connectionsPerHost is 10, and
     * threadsAllowedToBlockForConnectionMultiplier is 5, then 50 threads can block more than that and an exception will
     * be throw
     */
    private int threadsAllowedToBlockForConnectionMultiplier = MONGO_OPTIONS.threadsAllowedToBlockForConnectionMultiplier;

    /**
     * max wait time of a blocking thread for a connection
     */
    private int maxWaitTime = MONGO_OPTIONS.maxWaitTime;

    /**
     * connect timeout in milliseconds. 0 is default and infinite
     */
    private int connectTimeout = MONGO_OPTIONS.connectTimeout;

    /**
     * socket timeout. 0 is default and infinite
     */
    private int socketTimeout = MONGO_OPTIONS.socketTimeout;

    /**
     * This controls whether or not to have socket keep alive turned on (SO_KEEPALIVE).
     * 
     * defaults to false
     */
    public boolean socketKeepAlive = MONGO_OPTIONS.socketKeepAlive;

    /**
     * this controls whether or not on a connect, the system retries automatically
     */
    private boolean autoConnectRetry = MONGO_OPTIONS.autoConnectRetry;

    private long maxAutoConnectRetryTime = MONGO_OPTIONS.maxAutoConnectRetryTime;

    /**
     * This specifies the number of servers to wait for on the write operation, and exception raising behavior.
     * 
     * Defaults to 0.
     */
    private int writeNumber;

    /**
     * This controls timeout for write operations in milliseconds.
     * 
     * Defaults to 0 (indefinite). Greater than zero is number of milliseconds to wait.
     */
    private int writeTimeout;

    /**
     * This controls whether or not to fsync.
     * 
     * Defaults to false.
     */
    private boolean writeFsync;

    /**
     * Specifies if the driver is allowed to read from secondaries or slaves.
     * 
     * Defaults to false
     */
    @SuppressWarnings("deprecation")
    private boolean slaveOk = MONGO_OPTIONS.slaveOk;

    /**
     * number of connections allowed per host will block if run out
     */
    public void setConnectionsPerHost(int connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    /**
     * multiplier for connectionsPerHost for # of threads that can block if connectionsPerHost is 10, and
     * threadsAllowedToBlockForConnectionMultiplier is 5, then 50 threads can block more than that and an exception will
     * be throw
     */
    public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    /**
     * max wait time of a blocking thread for a connection
     */
    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    /**
     * connect timeout in milliseconds. 0 is default and infinite
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * socket timeout. 0 is default and infinite
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    /**
     * This controls whether or not to have socket keep alive
     * 
     * @param socketKeepAlive
     */
    public void setSocketKeepAlive(boolean socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
    }

    /**
     * This specifies the number of servers to wait for on the write operation, and exception raising behavior. The 'w'
     * option to the getlasterror command. Defaults to 0.
     * <ul>
     * <li>-1 = don't even report network errors</li>
     * <li>0 = default, don't call getLastError by default</li>
     * <li>1 = basic, call getLastError, but don't wait for slaves</li>
     * <li>2+= wait for slaves</li>
     * </ul>
     * 
     * @param writeNumber the number of servers to wait for on the write operation, and exception raising behavior.
     */
    public void setWriteNumber(int writeNumber) {
        this.writeNumber = writeNumber;
    }

    /**
     * This controls timeout for write operations in milliseconds. The 'wtimeout' option to the getlasterror command.
     * 
     * @param writeTimeout Defaults to 0 (indefinite). Greater than zero is number of milliseconds to wait.
     */
    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    /**
     * This controls whether or not to fsync. The 'fsync' option to the getlasterror command. Defaults to false.
     * 
     * @param writeFsync to fsync on write (true), otherwise false.
     */
    public void setWriteFsync(boolean writeFsync) {
        this.writeFsync = writeFsync;
    }

    /**
     * this controls whether or not on a connect, the system retries automatically
     */
    public void setAutoConnectRetry(boolean autoConnectRetry) {
        this.autoConnectRetry = autoConnectRetry;
    }

    /**
     * The maximum amount of time in millisecons to spend retrying to open connection to the same server. Default is 0,
     * which means to use the default 15s if autoConnectRetry is on.
     * 
     * @param maxAutoConnectRetryTime the maxAutoConnectRetryTime to set
     */
    public void setMaxAutoConnectRetryTime(long maxAutoConnectRetryTime) {
        this.maxAutoConnectRetryTime = maxAutoConnectRetryTime;
    }

    /**
     * Specifies if the driver is allowed to read from secondaries or slaves. Defaults to false.
     * 
     * @param slaveOk true if the driver should read from secondaries or slaves.
     */
    public void setSlaveOk(boolean slaveOk) {
        this.slaveOk = slaveOk;
    }

    @SuppressWarnings("deprecation")
    public void afterPropertiesSet() {
        MONGO_OPTIONS.connectionsPerHost = connectionsPerHost;
        MONGO_OPTIONS.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
        MONGO_OPTIONS.maxWaitTime = maxWaitTime;
        MONGO_OPTIONS.connectTimeout = connectTimeout;
        MONGO_OPTIONS.socketTimeout = socketTimeout;
        MONGO_OPTIONS.socketKeepAlive = socketKeepAlive;
        MONGO_OPTIONS.autoConnectRetry = autoConnectRetry;
        MONGO_OPTIONS.maxAutoConnectRetryTime = maxAutoConnectRetryTime;
        MONGO_OPTIONS.slaveOk = slaveOk;
        MONGO_OPTIONS.w = writeNumber;
        MONGO_OPTIONS.wtimeout = writeTimeout;
        MONGO_OPTIONS.fsync = writeFsync;
    }

    public MongoOptions getObject() {
        return MONGO_OPTIONS;
    }

    public Class<?> getObjectType() {
        return MongoOptions.class;
    }

    public boolean isSingleton() {
        return true;
    }

}