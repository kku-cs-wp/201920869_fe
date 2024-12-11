package kr.ac.kku.cs.wp.wsd.support.spring.exception;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.ac.kku.cs.wp.wsd.tools.message.MessageException;

/**
 * GlobalExceptionHandler
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(MessageException.class)
    public ResponseEntity<String> handleMessageException(MessageException e) {
		logger.debug(e.getMessage());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
		
        return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }
	
}