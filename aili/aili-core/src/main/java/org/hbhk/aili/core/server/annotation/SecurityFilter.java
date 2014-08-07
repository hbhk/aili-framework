package org.hbhk.aili.core.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)	// 运行时保留
@Target({ElementType.METHOD})		// 注解对象为方法
@Inherited //实现继承生效
public @interface SecurityFilter {
	
	boolean value() default  true;	// 参数名
}
