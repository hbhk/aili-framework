package org.hbhk.aili.core.share.consts;

public interface Protocol {
	/**
	 * 方法名定义
	 */
    String SECURITY_HEADER = "method-name";
    
    /**
     * json头定义
     */
    String JSON_CONTENT_TYPE = "application/json";
    
    /** 多媒体类型的前缀 */
    String CONTENT_TYPE_MULTIPART = "multipart/";
    
    /**
     * hessian头定义
     */
    String HESSIAN_CONTENT_TYPE = "x-application/hessian";

    
}
