package org.hbhk.aili.core.server.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hbhk.aili.core.server.context.DataSourceContextHolder;
import org.hbhk.aili.core.share.consts.DataSourceConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

@Aspect
@Component
public class DynamicDataSourceAspect {
	protected static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
	
	private TransactionAttributeSource transactionAttributeSouce;
	
	@Around("@target(org.springframework.transaction.annotation.Transactional)")
	public Object doQuery(ProceedingJoinPoint pjp) throws Throwable {
		if(transactionAttributeSouce==null){
			transactionAttributeSouce = new AnnotationTransactionAttributeSource();
		}
		MethodSignature ms = (MethodSignature)pjp.getSignature();
		TransactionAttribute ta = transactionAttributeSouce.getTransactionAttribute(ms.getMethod(), pjp.getTarget().getClass());
		if(ta == null || (DataSourceContextHolder.getDataSourceType() != null && ta.getPropagationBehavior() != TransactionDefinition.PROPAGATION_REQUIRES_NEW)){
			return pjp.proceed(pjp.getArgs());
		}
		
		logger.debug("determine datasource for query:{}.{}",ms.getDeclaringType().getName(),ms.getMethod().getName());		
		logger.debug("Current operation's transaction status: {}", ta == null ? "null": ta.toString());
		
		String currentStatus = DataSourceContextHolder.getDataSourceType();
		if(ta != null){
			if(ta.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRES_NEW){
				logger.debug("New writable connection is required for new transaction.");
				DataSourceContextHolder.setgetDataSourceType(DataSourceConst.WRITE);
			}else
				DataSourceContextHolder.setgetDataSourceType((ta != null && ta.isReadOnly()) ? DataSourceConst.READ: DataSourceConst.WRITE);
		}
		try {
			Object rtn = pjp.proceed(pjp.getArgs());
			return rtn;
		} finally {
			if(ta != null){
				if(ta.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRES_NEW){
					logger.debug("Fallback to previous Read/Write Status: {}", currentStatus);
					if(currentStatus == null)
						DataSourceContextHolder.remove();
					else
						DataSourceContextHolder.setgetDataSourceType(currentStatus);
				}else{
					logger.debug("Clear Read/Write Status");
					DataSourceContextHolder.remove();					
				}				
			}
		}
	
	}
}
