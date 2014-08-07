package org.hbhk.aili.core.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 运行时保留
@Target({ElementType.METHOD})		// 注解对象为方法
public @interface RenderMethod {

	public enum MethodType { INQUIRE };

	public MethodType method() default MethodType.INQUIRE;
	public RenderParameter[] parameters();	// 参数列表

}

