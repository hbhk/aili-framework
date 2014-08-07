package org.hbhk.aili.orm.server.spring;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

import org.hbhk.aili.orm.server.dao.GenericEntityDao;
import org.hbhk.aili.orm.server.surpport.ModelClassSupport;
import org.hbhk.aili.orm.server.surpport.SimpleModelClassSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.ReplaceOverride;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.w3c.dom.Element;

public class GenericDaoConfigBeanDefinitionParser implements BeanDefinitionParser{

	private static final Logger logger = LoggerFactory.getLogger(GenericDaoConfigBeanDefinitionParser.class);
	
	public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
	
	public static final String ID_ATTRIBUTE = "id";
	
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		if(element.hasAttribute(BASE_PACKAGE_ATTRIBUTE)){
			logger.debug("Start auto Dao detection...");
			//element.getAttribute(BASE_PACKAGE_ATTRIBUTE) "org.hbhk.aili.**.test" 扫描的包路径
			Set<BeanDefinition> beanDefinitions = getDaoInterfaces(element.getAttribute(BASE_PACKAGE_ATTRIBUTE) ,
						parserContext.getReaderContext().getResourceLoader());
			for(BeanDefinition bean: beanDefinitions){
				registerDao(bean, parserContext);
			}
		}
		return null;
	}
	
	private Set<BeanDefinition> getDaoInterfaces(String basePackage, final ResourceLoader resourceLoader){
		GenericEntityDaoComponentProvider p = new GenericEntityDaoComponentProvider();
		p.setResourceLoader(resourceLoader);
		return p.findCandidateComponents(basePackage);
	}
	
	private void registerDao(BeanDefinition bean, ParserContext parserContext){
		try {
			String className = bean.getBeanClassName();
			Class<?> clazz = Class.forName(bean.getBeanClassName());
			Class<?>[] interfaces;
			String beanName = getBeanName(clazz);
			AbstractBeanDefinition targetBean;			
			if(clazz.isInterface()){
				logger.debug("Add Interface '{}' to '{}'.", className, beanName);	
				Class<?> targetType = getGenericModelClass(clazz);
				if(targetType == null) throw new BeanCreationException("{} is not a valid GenericEntityDao bean.", beanName);
				
				targetBean = new RootBeanDefinition(SimpleModelClassSupport.class);
				targetBean.getPropertyValues().addPropertyValue("modelClass",targetType);
				interfaces = new Class<?>[]{clazz, ModelClassSupport.class};
			}else{
				logger.debug("Add Class '{}' to '{}'.", className, beanName);
				targetBean = new RootBeanDefinition(clazz);
				interfaces = clazz.getInterfaces();
				Method[] methods = targetBean.getBeanClass().getMethods();
				for (Method method : methods) {
					if (Modifier.isAbstract(method.getModifiers())) {
						targetBean.getMethodOverrides().addOverride(
								new ReplaceOverride(method.getName(), null));
					}
				}
			}			
			//bean.setAttribute(ID_ATTRIBUTE, beanName);
			AbstractBeanDefinition rootDefinition = new GenericBeanDefinition();
			//queryAspect genericDaoProxy
			rootDefinition.setParentName("genericDaoProxy");
			rootDefinition.getPropertyValues().addPropertyValue("proxyInterfaces",interfaces);
			rootDefinition.getPropertyValues().addPropertyValue("target",targetBean);	
			BeanComponentDefinition definition =
                new BeanComponentDefinition(rootDefinition, beanName);
			parserContext.registerBeanComponent(definition);
		} catch (ClassNotFoundException e) {
			//do nothing
		}
		
	}
	
	private String getBeanName(Class<?> clazz){
		if(clazz == null) throw new IllegalArgumentException();
		String name = clazz.getSimpleName();
		return name.substring(0,1).toLowerCase() + name.substring(1);
	}
	
	private Class<?> getGenericModelClass(Class<?> iClazz){
		for(Type type: iClazz.getGenericInterfaces()){
			if(type instanceof ParameterizedType)
				return (Class<?>)((ParameterizedType)type).getActualTypeArguments()[0];
		}
		return null;
	}

	static class GenericEntityDaoComponentProvider extends
		ClassPathScanningCandidateComponentProvider {

		public GenericEntityDaoComponentProvider() {
			super(false);
			addIncludeFilter(new AssignableTypeFilter(GenericEntityDao.class));
		}
		
		@Override
        protected boolean isCandidateComponent(
                AnnotatedBeanDefinition beanDefinition) {
			try {
				Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
				if(GenericEntityDao.class.equals(clazz)) return false;
				if(clazz.isInterface() || ((clazz.getModifiers() & Modifier.ABSTRACT) != 0)){
					return GenericEntityDao.class.isAssignableFrom(clazz);
				}else
					return false;
			} catch (ClassNotFoundException e) {
				return false;
			}
            
        }
	}
}
