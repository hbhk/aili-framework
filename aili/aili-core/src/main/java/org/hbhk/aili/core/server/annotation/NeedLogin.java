package org.hbhk.aili.core.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLogin{

	boolean value() default true;

	/**
	 * 是否支持游客登录
	 * true为支持游客登录
	 * @return
	 */
	boolean guest() default false;
	
	/**
	 * 用来确定没有登录后跳到哪里
	 * 如果有值，则使用returnUrl做为跳转，否则使用当前url做为跳转
	 * @return
	 */
	String returnUrl() default "";
}
