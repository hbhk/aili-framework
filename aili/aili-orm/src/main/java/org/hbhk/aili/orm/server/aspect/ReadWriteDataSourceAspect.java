package org.hbhk.aili.orm.server.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hbhk.aili.orm.server.datasource.ReadWriteStatusHolder;
import org.hbhk.aili.orm.server.datasource.ReadWriteSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

@Aspect
public class ReadWriteDataSourceAspect {
	protected static final Logger logger = LoggerFactory
			.getLogger(ReadWriteDataSourceAspect.class);

	@Autowired
	TransactionAttributeSource transactionAttributeSouce;

	@Around("this(org.hbhk.aili.orm.server.datasource.ReadWriteSupport)")
	public Object doQuery(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		TransactionAttribute ta = transactionAttributeSouce
				.getTransactionAttribute(ms.getMethod(), pjp.getTarget()
						.getClass());
		//没有事务或者 事务状态不是新建事务
		if (ta == null
				|| (ReadWriteStatusHolder.getReadWriteStatus() != null && ta
						.getPropagationBehavior() != TransactionDefinition.PROPAGATION_REQUIRES_NEW)) {
			return pjp.proceed(pjp.getArgs());
		}

		logger.debug("determine datasource for query:{}.{}", ms
				.getDeclaringType().getName(), ms.getMethod().getName());
		logger.debug("Current operation's transaction status: {}",
				ta == null ? "null" : ta.toString());

		String currentStatus = ReadWriteStatusHolder.getReadWriteStatus();
		if (ta != null) {
			//判断是否需要写数据
			if (ta.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRES_NEW) {
				logger.debug("New writable connection is required for new transaction.");
				ReadWriteStatusHolder
						.setReadWriteStatus(ReadWriteSupport.WRITE);
			} else{
				//有事务且是readOnly
				ReadWriteStatusHolder.setReadWriteStatus((ta != null && ta
						.isReadOnly()) ? ReadWriteSupport.READ
						: ReadWriteSupport.WRITE);
			}
				
		}
		try {
			Object rtn = pjp.proceed(pjp.getArgs());
			return rtn;
		} catch (Throwable e) {
			throw e;
		} finally {
		
			if (ta != null) {
				//新建事务类型
				if (ta.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRES_NEW) {
					logger.debug("Fallback to previous Read/Write Status: {}",
							currentStatus);
					if (currentStatus == null){
						//执行完之后清理 读写状态
						ReadWriteStatusHolder.clearReadWriteStatus();
					}else{
						//设置当前状态
						ReadWriteStatusHolder.setReadWriteStatus(currentStatus);
					}
				} else {
					logger.debug("Clear Read/Write Status");
					//执行完之后清理 读写状态
					ReadWriteStatusHolder.clearReadWriteStatus();
				}
			}
		}
	}

}
