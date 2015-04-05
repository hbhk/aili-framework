package org.hbhk.aili.core.server.aop;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hbhk.aili.core.server.context.DataSourceContextHolder;
import org.hbhk.aili.core.server.service.IDynamicDataSourceService;
import org.hbhk.aili.core.server.service.impl.DynamicDataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDataSourceAspect {
	protected static final Logger logger = LoggerFactory
			.getLogger(DynamicDataSourceAspect.class);

	private IDynamicDataSourceService dynamicDataSourceService;

	@Around("this(org.hbhk.aili.core.server.service.IBaseService)")
	public Object doExecute(ProceedingJoinPoint pjp) throws Throwable {
		if (dynamicDataSourceService == null) {
			dynamicDataSourceService = new DynamicDataSourceService();
		}
		String strategy = dynamicDataSourceService.getDataSourceStrategy(pjp);
		if (StringUtils.isEmpty(strategy)) {
			return pjp.proceed(pjp.getArgs()); 
		}
		try {
			logger.debug("数据源当前 Read/Write Status: {}",strategy);
			DataSourceContextHolder.setDataSourceType(strategy);
			Object rtn = pjp.proceed(pjp.getArgs());
			return rtn;
		} finally {
			DataSourceContextHolder.remove();
		}
	}
}
