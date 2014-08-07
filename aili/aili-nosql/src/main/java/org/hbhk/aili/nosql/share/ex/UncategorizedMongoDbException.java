package org.hbhk.aili.nosql.share.ex;

import org.springframework.dao.UncategorizedDataAccessException;
/**
 * 提供mongo出现未知异常的处理异常
 */
public class UncategorizedMongoDbException extends UncategorizedDataAccessException {

    private static final long serialVersionUID = -2336595514062364929L;

    public UncategorizedMongoDbException(String msg, Throwable cause) {
        super(msg, cause);
    }
}