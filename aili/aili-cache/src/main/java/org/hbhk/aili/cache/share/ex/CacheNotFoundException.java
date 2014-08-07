package org.hbhk.aili.cache.share.ex;



/**
 * Cache没找到异常
 */
public class CacheNotFoundException extends GeneralException {
	
	private static final long serialVersionUID = -8573419783281346196L;

	public CacheNotFoundException(String msg) {
        super(msg);
    }
	
	public CacheNotFoundException(Throwable e) {
	    super(e);
	}
}
