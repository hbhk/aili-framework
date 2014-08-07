package org.hbhk.aili.nosql.share.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.nosql.server.mongodb.MongoSynchronization;
import org.hbhk.aili.nosql.server.tx.DbHolder;
import org.hbhk.aili.nosql.share.ex.CannotGetMongoDbConnectionException;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import com.mongodb.DB;
import com.mongodb.Mongo;

/**
 * 提供操作mongo数据库的工具类
 */
public abstract class MongoDbUtils {
	private static final Log LOGGER = LogFactory.getLog(MongoDbUtils.class);

	/**
     */
	private MongoDbUtils() {

	}

	public static DB getDB(Mongo mongo, String databaseName) {
		return doGetDB(mongo, databaseName, null, null, true);
	}

	public static DB getDB(Mongo mongo, String databaseName, String username,
			char[] password) {
		return doGetDB(mongo, databaseName, username, password, true);
	}

	public static DB doGetDB(Mongo mongo, String databaseName, String username,
			char[] password, boolean allowCreate) {
		Assert.notNull(mongo, "No Mongo instance specified");

		DbHolder dbHolder = (DbHolder) TransactionSynchronizationManager
				.getResource(mongo);
		if (dbHolder != null && !dbHolder.isEmpty()) {
			// pre-bound Mongo DB
			DB db = null;
			if (TransactionSynchronizationManager.isSynchronizationActive()
					&& dbHolder.doesNotHoldNonDefaultDB()) {
				// Spring transaction management is active ->
				db = dbHolder.getDB();
				if (db != null && !dbHolder.isSynchronizedWithTransaction()) {
					LOGGER.debug("Registering Spring transaction synchronization for existing Mongo DB");
					TransactionSynchronizationManager
							.registerSynchronization(new MongoSynchronization(
									dbHolder, mongo));
					dbHolder.setSynchronizedWithTransaction(true);
				}
			}
			if (db != null) {
				return db;
			}
		}

		LOGGER.trace("Getting Mongo Database name=[" + databaseName + "]");
		DB db = mongo.getDB(databaseName);

		boolean credentialsGiven = username != null && password != null;
		if (credentialsGiven && !db.isAuthenticated()) {
			// Note, can only authenticate once against the same com.mongodb.DB
			// object.
			if (!db.authenticate(username, password)) {
				throw new CannotGetMongoDbConnectionException(
						"Failed to authenticate to database [" + databaseName
								+ "], username = [" + username
								+ "], password = [" + new String(password)
								+ "]", databaseName, username, password);
			}
		}

		// Use same Session for further Mongo actions within the transaction.
		// Thread object will get removed by synchronization at transaction
		// completion.
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			// We're within a Spring-managed transaction, possibly from
			// JtaTransactionManager.
			LOGGER.debug("Registering Spring transaction synchronization for new Hibernate Session");
			DbHolder holderToUse = dbHolder;
			if (holderToUse == null) {
				holderToUse = new DbHolder(db);
			} else {
				holderToUse.addDB(db);
			}
			TransactionSynchronizationManager
					.registerSynchronization(new MongoSynchronization(
							holderToUse, mongo));
			holderToUse.setSynchronizedWithTransaction(true);
			if (holderToUse != dbHolder) {
				TransactionSynchronizationManager.bindResource(mongo,
						holderToUse);
			}
		}

		// Check whether we are allowed to return the DB.
		if (!allowCreate && !isDBTransactional(db, mongo)) {
			throw new IllegalStateException(
					"No Mongo DB bound to thread, "
							+ "and configuration does not allow creation of non-transactional one here");
		}

		return db;
	}

	/**
	 * Return whether the given DB instance is transactional, that is, bound to
	 * the current thread by Spring's transaction facilities.
	 * 
	 * @param db
	 *            the DB to check
	 * @param mongo
	 *            the Mongo instance that the DB was created with (may be
	 *            <code>null</code>)
	 * @return whether the DB is transactional
	 */
	public static boolean isDBTransactional(DB db, Mongo mongo) {
		if (mongo == null) {
			return false;
		}
		DbHolder dbHolder = (DbHolder) TransactionSynchronizationManager
				.getResource(mongo);
		return (dbHolder != null && dbHolder.containsDB(db));
	}

	/**
	 * Perform actual closing of the Mongo DB object, catching and logging any
	 * cleanup exceptions thrown.
	 * 
	 * @param db
	 *            the DB to close (may be <code>null</code>)
	 */
	public static void closeDB(DB db) {
		if (db != null) {
			LOGGER.debug("Closing Mongo DB object");
			try {
				db.requestDone();
			} catch (Throwable ex) {
				LOGGER.debug("Unexpected exception on closing Mongo DB object",
						ex);
			}
		}
	}
}
