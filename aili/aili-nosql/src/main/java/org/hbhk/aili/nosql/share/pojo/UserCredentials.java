package org.hbhk.aili.nosql.share.pojo;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
/**
 *  登录mongo 数据库所需的用户名和密码
 */
public class UserCredentials {

    private final String username;
    private final String password;

    public UserCredentials() {
        this(null, null);
    }

    /**
     * Creates a new {@link UserCredentials} instance from the given username and password. Empty {@link String}s provided
     * will be treated like no username or password set.
     * 
     * @param username
     * @param password
     */
    public UserCredentials(String username, String password) {
        this.username = StringUtils.hasText(username) ? username : null;
        this.password = StringUtils.hasText(password) ? password : null;
    }

    /**
     * Get the username to use for authentication.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the password to use for authentication.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }

        UserCredentials that = (UserCredentials) obj;

        return ObjectUtils.nullSafeEquals(this.username, that.username)
                && ObjectUtils.nullSafeEquals(this.password, that.password);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int result = 17;

        result += 31 * ObjectUtils.nullSafeHashCode(username);
        result += 31 * ObjectUtils.nullSafeHashCode(password);

        return result;
    }
}
