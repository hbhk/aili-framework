package org.hbhk.aili.orm.server;

import org.hbhk.aili.core.server.web.WebApplicationContextHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

public class CustomBaseServiceFactory {
	private static final String BEAN_POSTFIX = ".service";

	public BaseService getService(String className) {
		String beanName = className + BEAN_POSTFIX;
		ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) WebApplicationContextHolder
				.getWebApplicationContext();
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
				.getBeanFactory();
		if (!beanFactory.containsBean(beanName)) {
			BeanDefinitionBuilder bdb = BeanDefinitionBuilder
					.rootBeanDefinition(className);
			bdb.setScope("prototype");
			beanFactory.registerBeanDefinition(beanName,
					bdb.getBeanDefinition());
		}
		return (BaseService) beanFactory.getBean(beanName);
	}
}