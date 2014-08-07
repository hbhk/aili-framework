package org.hbhk.aili.cache.server;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public abstract class CacheBase<K,V> implements ICache<String, V>,InitializingBean, DisposableBean  {
	
    
    /** 
     * 销毁方法
     * @throws Exception 
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
        
    }
	public abstract V doSet(String key);

}
