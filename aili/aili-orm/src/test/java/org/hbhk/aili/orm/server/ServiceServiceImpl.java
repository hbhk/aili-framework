package org.hbhk.aili.orm.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

public class ServiceServiceImpl implements ApplicationContextAware {

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

	public void addBeanService(Service service) throws Exception {
		if (!context.containsBean(service.value())) {
			Class<?> serviceClass = getServiceClass(service.value());
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
					.genericBeanDefinition(serviceClass);
			beanDefinitionBuilder.addPropertyValue("servicename",
					service.value());

			registerBean(service.value(),
					beanDefinitionBuilder.getRawBeanDefinition());
		}

	}

	/**
	 * @desc 向spring容器注册bean
	 * @param beanName
	 * @param beanDefinition
	 */
	private void registerBean(String beanName, BeanDefinition beanDefinition) {
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
		BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
				.getBeanFactory();
		beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);
	}

	/**
	 * @desc 根据类名查找class
	 * @param className
	 * @return
	 * @throws BVSException
	 */
	private Class<?> getServiceClass(String className) throws Exception {
		try {
			return Thread.currentThread().getContextClassLoader()
					.loadClass(className);
		} catch (ClassNotFoundException e) {
			// log.error("not found service class:" + className, e);
			throw new Exception("not found service class:" + className, e);
		}
	}
}