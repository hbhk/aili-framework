package org.hbhk.aili.core.server.service;

import org.aspectj.lang.ProceedingJoinPoint;

public interface IDynamicDataSourceService {
	/**
	 * 
	* @author 何波
	* @Description: 返回空无需处理 
	* @param pjp
	* @return
	* @throws Throwable   
	* Object   
	* @throws
	 */
	String getDataSourceStrategy(ProceedingJoinPoint pjp) throws Throwable;
}
