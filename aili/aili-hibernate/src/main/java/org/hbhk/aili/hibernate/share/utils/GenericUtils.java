package org.hbhk.aili.hibernate.share.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 */
public class GenericUtils {
	private static final Log log = LogFactory.getLog(GenericUtils.class);
	private GenericUtils(){
	}
	
	public static Class<?> getSuperClassGenericType(Class<?> clazz){
		return getSuperClassGenericType(clazz, 0);
	}
	public static Class<?> getSuperClassGenericType(Class<?> clazz,int index){
		Type genType = clazz.getGenericSuperclass();
		if(!(genType instanceof ParameterizedType)) {
			log.warn(clazz.getSimpleName());
			return Object.class;
		}
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		if( index >= params.length || index < 0){
			return Object.class;
		}
		
		if(!(params[index] instanceof Class)){
			return Object.class;
		}
		return (Class<?>)params[index];
	}
}
