package org.hbhk.aili.nosql.server.mongodb;

import org.hbhk.aili.nosql.share.ex.UncategorizedMongoDbException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import com.mongodb.MongoException;
import com.mongodb.MongoException.CursorNotFound;
import com.mongodb.MongoException.DuplicateKey;
import com.mongodb.MongoException.Network;
import com.mongodb.MongoInternalException;
/**
 * 为mongo数据库提供持久化异常支持
 */
public class MongoExceptionTranslator implements PersistenceExceptionTranslator {

    /*
      * (non-Javadoc)
      *
      * @see org.springframework.dao.support.PersistenceExceptionTranslator#
      * translateExceptionIfPossible(java.lang.RuntimeException)
      */
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {

        // Check for well-known MongoException subclasses.

        // All other MongoExceptions
        if (ex instanceof DuplicateKey) {
            return new DuplicateKeyException(ex.getMessage(), ex);
        }
        if (ex instanceof Network) {
            return new DataAccessResourceFailureException(ex.getMessage(), ex);
        }
        if (ex instanceof CursorNotFound) {
            return new DataAccessResourceFailureException(ex.getMessage(), ex);
        }
        if (ex instanceof MongoException) {
            int code = ((MongoException) ex).getCode();
            if (code == 11000 || code == 11001) {
                throw new DuplicateKeyException(ex.getMessage(), ex);
            } else if (code == 12000 || code == 13440) {
                throw new DataAccessResourceFailureException(ex.getMessage(), ex);
            } else if (code == 10003 || code == 12001 || code == 12010 || code == 12011 || code == 12012) {
                throw new InvalidDataAccessApiUsageException(ex.getMessage(), ex);
            }
            return new UncategorizedMongoDbException(ex.getMessage(), ex);
        }
        if (ex instanceof MongoInternalException) {
            return new InvalidDataAccessResourceUsageException(ex.getMessage(), ex);
        }

        // If we get here, we have an exception that resulted from user code,
        // rather than the persistence provider, so we return null to indicate
        // that translation should not occur.
        return null;
    }
}
