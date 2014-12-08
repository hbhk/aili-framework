package org.hbhk.aili.core.server.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.hbhk.aili.core.share.pojo.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.WebApplicationContext;

public abstract class BaseController {

	public WebApplicationContext getWebApplicationContext() {
		return WebApplicationContextHolder.getWebApplicationContext();
	}

	public ResponseEntity returnSuccess() {
		return new ResponseEntity();
	}

	public ResponseEntity returnSuccess(String msg, Object result,
			String dealUrl) {
		ResponseEntity response = new ResponseEntity();
		response.setMsg(msg);
		response.setResult(result);
		response.setDealUrl(dealUrl);
		return response;
	}

	public ResponseEntity returnSuccess(Object result) {
		ResponseEntity response = new ResponseEntity();
		response.setResult(result);
		return response;
	}

	public ResponseEntity returnSuccess(String msg) {
		ResponseEntity response = new ResponseEntity();
		response.setMsg(msg);
		return response;
	}

	public ResponseEntity returnException() {
		ResponseEntity response = new ResponseEntity();
		response.setException(true);
		response.setSuccess(false);
		return response;
	}

	public ResponseEntity returnException(String msg) {
		ResponseEntity response = new ResponseEntity();
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
