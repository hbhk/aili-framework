package org.hbhk.aili.core.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)	// 运行时保留
@Target({ElementType.METHOD})		// 注解对象为方法
public @interface RenderParameter {

	// 参数类型
	public enum ParameterType { STRING, SHORT, INT, BOOL, LONG, OBJECT };
	// 参数值的来源
	public enum ScopeType { NORMAL, SESSION, COOKIE, ATTRIBUTE, CUSTOM };
	public String name();	// 参数名
	public boolean ignoreCase() default false;	// 匹配时是否忽略大小写
	public ParameterType type() default ParameterType.STRING;	// 参数类型
	public ScopeType scope() default ScopeType.NORMAL;		// 参数值来源

}


