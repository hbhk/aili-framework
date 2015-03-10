package org.hbhk.aili.core.server.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.hbhk.aili.core.share.entity.ResultEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.WebApplicationContext;

public abstract class BaseController {

	public WebApplicationContext getWebApplicationContext() {
		return WebApplicationContextHolder.getWebApplicationContext();
	}

	public ResultEntity returnSuccess() {
		return new ResultEntity();
	}

	public ResultEntity returnSuccess(String msg, Object result,
			String dealUrl) {
		ResultEntity response = new ResultEntity();
		response.setMsg(msg);
		response.setResult(result);
		response.setDealUrl(dealUrl);
		return response;
	}

	public ResultEntity returnSuccess(Object result) {
		ResultEntity response = new ResultEntity();
		response.setResult(result);
		return response;
	}

	public ResultEntity returnSuccess(String msg) {
		ResultEntity response = new ResultEntity();
		response.setMsg(msg);
		return response;
	}

	public ResultEntity returnException() {
		ResultEntity response = new ResultEntity();
		response.setException(true);
		response.setSuccess(false);
		return response;
	}

	public ResultEntity returnException(String msg) {
		ResultEntity response = new ResultEntity();
		response.setException(true);
		response.setSuccess(false);
		response.setMsg(msg);
		return response;
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

}
