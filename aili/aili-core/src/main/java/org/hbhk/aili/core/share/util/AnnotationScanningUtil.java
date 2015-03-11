package org.hbhk.aili.core.share.util;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class AnnotationScanningUtil {
	
	private static  Map<String, List<String>> urlsCache = new ConcurrentHashMap<String, List<String>>();
	/**
	 * 获取指定注解的所有类
	 * 
	 * @param annotation
	 * @param scannPackage
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>>  getAnnotatedClasses(Class<? extends Annotation> annotation,
			String... scannPackage) throws ClassNotFoundException {
		// 是否使用默认过滤器true使用
		ClassPathScanningCandidateComponentProvider packageScan = new ClassPathScanningCandidateComponentProvider(
				false);
		packageScan.addIncludeFilter(new AnnotationTypeFilter(annotation));
		if (scannPackage == null) {
			return null;
		}
		List<Class<?>> annotatedClasses = new ArrayList<Class<?>>();
		for (String pack : scannPackage) {
			Set<BeanDefinition> bds = packageScan.findCandidateComponents(pack);
			for (BeanDefinition beanDefinition : bds) {
				annotatedClasses.add(Class.forName(beanDefinition
						.getBeanClassName()));
			}
		}
		return annotatedClasses;
	}
	
	
/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
	public static List<String> getRequestMappingUrls(Class<? extends Annotation> annotation,
			String... scannPackage){
		if(annotation==null || scannPackage==null || scannPackage.length==0){
			return null;
		}
		StringBuilder key = new StringBuilder();
		key.append(annotation.getSimpleName());
		for (String str : scannPackage) {
			key.append(str);
		}
		if(urlsCache.get(key.toString()) != null){
			return urlsCache.get(key.toString());
		}
		List<Class<?>> cls = null;
		try {
			cls = getAnnotatedClasses(annotation, scannPackage);
		} catch (ClassNotFoundException e) {
			throw  new RuntimeException(e);
		}
		List<String>  urls = new ArrayList<String>();
		for (Class<?> c : cls) {
			RequestMapping classMapping = c.getAnnotation(RequestMapping.class);
		    Method[] ms = c.getMethods();
		    for (Method m : ms) {
		    	RequestMapping methodMapping = m.getAnnotation(RequestMapping.class);
		    	if(methodMapping == null){
		    		continue;
		    	}
		    	String[] subfixUrls =  methodMapping.value();
		    	if(subfixUrls == null || subfixUrls.length==0){
		    		continue;
				}
		    	String prifixUrl = null;
		    	String subfixUrl = null;
				if(classMapping != null){
					String[] prifixUrls = classMapping.value();
					if(prifixUrls != null && prifixUrls.length > 0){
						prifixUrl = prifixUrls[0];
					}
					subfixUrl = subfixUrls[0];
					if(prifixUrl != null && subfixUrl!=null){
						urls.add(prifixUrl+subfixUrl);
					}else if(subfixUrl!=null){
						urls.add(prifixUrl+subfixUrl);
					}
				}else{
					subfixUrl = subfixUrls[0];
					if(subfixUrl != null){
						urls.add(subfixUrl);
					}
				}
			}
		}
		urlsCache.put(key.toString(), urls);
		return urls;
		
	}

}

