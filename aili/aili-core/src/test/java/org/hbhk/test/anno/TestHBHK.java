package org.hbhk.test.anno;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.server.annotation.RenderMethod;
import org.hbhk.aili.core.server.annotation.RenderParameter;
import org.hbhk.aili.core.server.annotation.RenderParameter.ScopeType;
import org.hbhk.aili.core.server.annotation.SecurityFilter;

public class TestHBHK {

	private static Log log = LogFactory.getLog(TestHBHK.class);

	@RenderMethod(parameters = {
			@RenderParameter(name = "logined", scope = ScopeType.SESSION),
			@RenderParameter(name = "loginedUser", scope = ScopeType.SESSION) })
	public void inquire(String logined, String loginedUser) {
		if ("true".equals(logined)) {
			System.out.println(loginedUser + " is logined.");
		} else {
			System.out.println("No user logined.");
		}
	}
 
	@SecurityFilter()
	public  void   saveUser(String name,String password){
		System.out.println("保存成功");
	}
	
	public static void main(String[] args) throws Exception {
		TestHBHK renderer = new TestHBHK();
		for (Method method : renderer.getClass().getDeclaredMethods()) {
			SecurityFilter rm = (SecurityFilter) method.getAnnotation(SecurityFilter.class);

			if (rm != null) {
				try {
			    //获取到配置的权限
				boolean  sec = rm.value();
				if(!sec){
					throw new Exception("权限不足");
				}
				//设置参数，为了测试 这里手动赋值 ，实际中从前端传来
				Object[]  parameters = new String[]{"hbhk","123456"};
				//回调要执行的方法
				method.invoke(renderer, parameters);
				} catch (IllegalArgumentException e) {
					log.error(e.getMessage());
				} catch (IllegalAccessException e) {
					log.error(e.getMessage());
				} catch (InvocationTargetException e) {
					log.error(e.getMessage());
				}

				break;
			}
		}

	}

	private static Object[] buildParameters(RenderParameter[] parameters) {
		Object[] objs = new Object[parameters.length];
		int i = 0;
		Request request = new Request();
		for (RenderParameter parameter : parameters) {
			ScopeType scope = parameter.scope();

			// 参数值来自request.getParameter
			if (scope == ScopeType.NORMAL) {
				String temp = request.getParameter(parameter.name());
				String value = null;

				if (temp != null && !"".equals(temp)) {
					try {
						byte[] bytes = temp.getBytes("iso-8859-1");
						value = new String(bytes, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						log.error(e.getMessage());
					}
				}

				objs[i++] = value;

				// 参数值来自Session
			} else if (scope == ScopeType.SESSION) {
				 objs[i ++] ="true";
				 parameter.name();
				// request.getSession().getAttribute(parameter.name());

				// 参数值来自Cookie
			} else if (scope == ScopeType.COOKIE) {
//				 for(Cookie cookie : request.getCookies()) {
//				 if(cookie.getName().equals(parameter.name())) {
				 objs[i ++] = "hbhk-COOKIE";//cookie.getValue();
//				 break;
//				 }
//				 }

				// 参数值来自request. getAttribute
			} else if (scope == ScopeType.ATTRIBUTE) {
				objs[i ++] = "hbhk-ATTRIBUTE";//request.getAttribute(parameter.name());
			}
		}

		return objs;
	}

}
