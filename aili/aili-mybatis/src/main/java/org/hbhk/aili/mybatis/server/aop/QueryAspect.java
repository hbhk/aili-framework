package org.hbhk.aili.mybatis.server.aop;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hbhk.aili.mybatis.server.support.Pagination;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QueryAspect implements Ordered, InitializingBean {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	public void afterPropertiesSet() throws Exception {
	}

	public int getOrder() {
		return 20;
	}
	//@Around("this(org.hbhk.aili.mybatis.server.dao.IBaseDao)")
	@Around("execution(* org.hbhk.*.*.server.dao.*.getPagination(..))")
	public Object doQuery(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		Method method = ms.getMethod();
		Class<?> rt = method.getReturnType();
		if(rt.isAssignableFrom(Pagination.class)){
			String clsName =  method.getDeclaringClass().getName();
			String methodName = method.getName();
			Pagination<Object> pagination = new Pagination<Object>();
			Object[] args = pjp.getArgs();
			String statement = clsName+"."+methodName;
			List<Object> list = sqlSessionFactory.openSession().selectList(statement, "");
			pagination.setItems(list);
			return pagination;
		}else{
			Object obj = pjp.proceed(pjp.getArgs());
			return obj;
		}
		
	}
}
