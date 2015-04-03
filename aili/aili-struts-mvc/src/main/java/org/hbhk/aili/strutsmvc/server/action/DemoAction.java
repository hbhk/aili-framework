/**
 *  Namespace：指定命名空间。

	ParentPackage：指定父包。
	
	Result：提供了Action结果的映射。（一个结果的映射）
	
	Results：“Result”注解列表
	
	ResultPath：指定结果页面的基路径。
	
	Action：指定Action的访问URL。
	
	Actions：“Action”注解列表。
	
	ExceptionMapping：指定异常映射。（映射一个声明异常）
	
	ExceptionMappings：一级声明异常的数组。
	
	InterceptorRef：拦截器引用。
	
	InterceptorRefs：拦截器引用组。
 */
package org.hbhk.aili.strutsmvc.server.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@Namespace("/aili")
@ParentPackage("aili-struts")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
@Results({ @Result(name = "success", location = "/main.jsp"),
		@Result(name = "error", location = "/error.jsp") })
public class DemoAction extends BaseAction {

	private static final long serialVersionUID = -5575404485843862621L;

	private String loginName;

	private String password;

	@Action("login")
	public String login() throws Exception {

		if ("hbhk".equals(loginName) && "135246".equals(password)) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	 @Action(value = "add", results = { @Result(name = "success", location = "index.jsp") })
	public String add() throws Exception {

		return SUCCESS;

	}

	public String getLoginName() {

		return loginName;

	}

	public void setLoginName(String loginName) {

		this.loginName = loginName;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}
}
