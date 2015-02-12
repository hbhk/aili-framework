package org.hbhk.aili.mybatis.server.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QueryAspect implements Ordered, InitializingBean {

	public void afterPropertiesSet() throws Exception {
	}

	public int getOrder() {
		return 20;
	}

	@Around("this(org.hbhk.aili.mybatis.server.dao.IBaseDao)")
	public Object doQuery(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		Method method = ms.getMethod();
		String methodName = method.getName();
		
		return pjp.proceed(pjp.getArgs());
	}
}
