package org.hbhk.aili.core.server.ex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.share.ex.BusinessException;
import org.hbhk.aili.core.share.pojo.ExceptionEntity;
import org.hbhk.aili.core.share.util.WebErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

	private Log log = LogFactory.getLog(getClass());

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ExceptionEntity> handleCustomException(
			BusinessException ex) {

		ExceptionEntity entity = new ExceptionEntity();
		entity.setCode(ex.getErrorCode());
		entity.setMsg(ex.getMessage());

		ResponseEntity<ExceptionEntity> httpEntity = new ResponseEntity<ExceptionEntity>(
				entity, HttpStatus.BAD_REQUEST);

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
