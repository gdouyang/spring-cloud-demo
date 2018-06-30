package microtortoise.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerErrorHander {

	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<Msg<?>> exceptionHandler(Exception e) {
		log.error(e.getMessage(), e);

		Msg<?> body = new Msg<Object>();
		body.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		ResponseEntity<Msg<?>> entity = new ResponseEntity<Msg<?>>(body, HttpStatus.INTERNAL_SERVER_ERROR);

		return entity;
	}
}
