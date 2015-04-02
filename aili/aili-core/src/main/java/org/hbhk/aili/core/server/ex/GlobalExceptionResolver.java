package org.hbhk.aili.core.server.ex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.server.web.WebApplicationContextHolder;
import org.hbhk.aili.core.share.entity.ExceptionEntity;
import org.hbhk.aili.core.share.entity.ResultEntity;
import org.hbhk.aili.core.share.ex.BusinessException;
import org.hbhk.aili.core.share.util.WebErrorUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
@ControllerAdvice
public class GlobalExceptionResolver {

	private Log log = LogFactory.getLog(getClass());

	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ResultEntity> handleCustomException(
			BusinessException ex) {

		ResultEntity entity = new ResultEntity();
		entity.setSuccess(false);
		entity.setException(true);
		String errCode = ex.getErrCode();
		if(errCode!=null && errCode!=""){
			WebApplicationContext	context = WebApplicationContextHolder.getWebApplicationContext();
			String msg =context.getMessage(errCode, null,
						LocaleContextHolder.getLocale());
			entity.setMsg(msg);
		}else{
			entity.setMsg(ex.getErrMsg());
		}
		ResponseEntity<ResultEntity> httpEntity = new ResponseEntity<ResultEntity>(
				entity, HttpStatus.OK);

		return httpEntity;

	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ExceptionEntity> handleAllException(Throwable ex) {
		log.error(ex.getMessage(), ex);
		ExceptionEntity entity = new ExceptionEntity();
		entity.setCode("500");
		entity.setMsg(WebErrorUtils.getStackTrace(ex));
		ResponseEntity<ExceptionEntity> httpEntity = new ResponseEntity<ExceptionEntity>(
				entity, HttpStatus.INTERNAL_SERVER_ERROR);

		return httpEntity;

	}
}
