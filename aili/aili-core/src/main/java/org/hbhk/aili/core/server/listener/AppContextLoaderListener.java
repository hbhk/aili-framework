package org.hbhk.aili.core.server.listener;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.hbhk.aili.core.share.consts.AppConst;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;



/**
 * 扩展spring的ContextLoaderListener
 * 在容器启动时增加配置参数
 * 
 * 配合使用方法：
 * web.xml中增加
 * <context-param>
 *      <param-name>override</param-name>
 *      <param-value>false</param-value>
 * </context-param>
 * <listener>
 *      <listener-class>org.hbhk.aili.core.server.listener.AppContextLoaderListener</listener-class>
 * </listener>
 */
public class AppContextLoaderListener extends ContextLoaderListener {

    /** 
     * 自定义扩展context参数
     */
    @Override
    protected void customizeContext(ServletContext servletContext,
            ConfigurableWebApplicationContext applicationContext) {
        XmlWebApplicationContext context = (XmlWebApplicationContext) applicationContext;  
        String override = servletContext.getInitParameter(AppConst.ALLOW_BEAN_DEFINITION_OVERRIDING);
        if(StringUtils.isNotBlank(override)) {
            context.setAllowBeanDefinitionOverriding(Boolean.parseBoolean(override));
        } else {
            context.setAllowBeanDefinitionOverriding(true); 
        }
    }

}
