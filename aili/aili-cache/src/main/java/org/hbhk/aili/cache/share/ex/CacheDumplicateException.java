package org.hbhk.aili.cache.share.ex;



/**
 * CacheId重复异常
 */
public class CacheDumplicateException extends GeneralException {

	private static final long serialVersionUID = -8867072528054610442L;
	
	public CacheDumplicateException(String msg) {
        super(msg);
    }
	
	public CacheDumplicateException(Throwable e) {
	    super(e);
	}

}
