package org.hbhk.aili.orm.server.aspect;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hbhk.aili.orm.server.annotation.NativeQuery;
import org.hbhk.aili.orm.server.handler.NativeQueryHandler;
import org.hbhk.aili.orm.server.service.IDaoService;
import org.springframework.beans.factory.annotation.Autowired;

public class QueryInterceptor implements MethodInterceptor {

	@Autowired
	private IDaoService daoService;

	private NativeQueryHandler nativeQueryHandler;

	@PostConstruct
	public void init() throws Exception {
		nativeQueryHandler = new NativeQueryHandler(daoService);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method m = invocation.getMethod();
		NativeQuery nativeQuery = m.getAnnotation(NativeQuery.class);
		if (nativeQuery != null) {
			return nativeQueryHandler
					.handleNativeQuery(nativeQuery, invocation);
		} else {
			return invocation.proceed();
		}
	}

}