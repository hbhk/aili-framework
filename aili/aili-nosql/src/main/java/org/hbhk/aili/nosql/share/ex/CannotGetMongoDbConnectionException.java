package org.hbhk.aili.nosql.share.ex;

import org.springframework.dao.DataAccessResourceFailureException;
/**
 * 
 * 描述mongoDB数据库是否正常运行的异常
 */
public class CannotGetMongoDbConnectionException extends DataAccessResourceFailureException {
   //mongo用户名
    private String username;
    //密码
    private char[] password;
   //数据库名称
    private String database;

    private static final long serialVersionUID = 1172099106475265589L;

    public CannotGetMongoDbConnectionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CannotGetMongoDbConnectionException(String msg) {
        super(msg);
    }

    public CannotGetMongoDbConnectionException(String msg, String database, String username, char[] password) {
        super(msg);
        this.username = username;
        this.password = password == null ? null : password.clone();
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public char[] getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

}