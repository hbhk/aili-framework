package org.hbhk.aili.orm.server.intercptor;

import javax.servlet.http.HttpServletRequest;

import org.hbhk.aili.orm.server.page.QueryBeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class QueryBeanModelMethodProcessor implements
		HandlerMethodArgumentResolver {

	public QueryBeanModelMethodProcessor() {
    }
	
	protected Object createAttribute(String attributeName, MethodParameter parameter,
			WebDataBinderFactory binderFactory,  NativeWebRequest request) throws Exception {

		return BeanUtils.instantiateClass(parameter.getParameterType());
	}
	
	protected String getNameForParameter(MethodParameter parameter) {
		QueryBeanParam annot = parameter.getParameterAnnotation(QueryBeanParam.class);
		String attrName = (annot != null) ? annot.value() : null;
		return StringUtils.hasText(attrName) ? attrName :  Conventions.getVariableNameForParameter(parameter);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest request,
			WebDataBinderFactory binderFactory) throws Exception {
		
		 HttpServletRequest nativeRequest = (HttpServletRequest) request.getNativeRequest();
		return QueryBeanUtil.parseParameter(nativeRequest);
	}


	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		 if (parameter.hasParameterAnnotation(QueryBeanParam.class)) {
	            return true;
	        }
	       return false;
	}

}
