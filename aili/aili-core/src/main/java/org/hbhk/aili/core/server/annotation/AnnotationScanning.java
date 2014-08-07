package org.hbhk.aili.core.server.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class AnnotationScanning {

	private static AnnotationScanning scanning;

	private AnnotationScanning() {
	}

	public static AnnotationScanning getInstance() {
		if (scanning == null) {
			scanning = new AnnotationScanning();
		}
		return scanning;
	}

	/**
	 * 获取指定注解的所有类
	 * 
	 * @param annotation
	 * @param scannPackage
	 * @return
	 * @throws ClassNotFoundException
	 */
	public List<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotation,
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

}
