package org.hbhk.aili.core.server.service.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.hbhk.aili.core.server.context.DataSourceContextHolder;
import org.hbhk.aili.core.server.service.IDynamicDataSourceService;
import org.hbhk.aili.core.share.consts.DataSourceConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

public class DynamicDataSourceService implements IDynamicDataSourceService {
	
	protected static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceService.class);
	private TransactionAttributeSource transactionAttributeSouce;
	
	@Override
	public String getDataSourceStrategy(ProceedingJoinPoint pjp) throws Throwable {
		if(transactionAttributeSouce==null){
			transactionAttributeSouce = new AnnotationTransactionAttributeSource();
		}
		MethodSignature ms = (MethodSignature)pjp.getSignature();
		TransactionAttribute ta = transactionAttributeSouce.getTransactionAttribute(ms.getMethod(), pjp.getTarget().getClass());
		if(ta == null || (DataSourceContextHolder.getDataSourceType() != null && ta.getPropagationBehavior() != TransactionDefinition.PROPAGATION_REQUIRES_NEW)){
			return null;
		}
		logger.debug("determine datasource for query:{}.{}",ms.getDeclaringType().getName(),ms.getMethod().getName());		
		logger.debug("Current operation's transaction status: {}", ta == null ? "null": ta.toString());
		
		String currentStatus = DataSourceContextHolder.getDataSourceType();
		if(ta != null){
			if(ta.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRES_NEW){
				logger.debug("New writable connection is required for new transaction.");
				currentStatus = DataSourceConst.WRITE;
			}else{
				currentStatus = (ta != null && ta.isReadOnly()) ? DataSourceConst.READ: DataSourceConst.WRITE;
			}
		}
		return currentStatus;
	}
}
