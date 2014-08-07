package org.hbhk.aili.cache.server;



public class CacheSupportTest  extends  CacheSupport<RoleInfo>{

	@Override
	public String getCacheId() {
		
		return "hbhk";
	}

	@Override
	public RoleInfo doSet(String key) {
		
		RoleInfo   r = new RoleInfo();
		r.setCode("asdas");
		r.setId("sdfdsf");;
		return r;
	}

}
