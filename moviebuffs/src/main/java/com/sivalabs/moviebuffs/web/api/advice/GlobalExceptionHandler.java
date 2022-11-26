package com.sivalabs.moviebuffs.web.api.advice;

import com.sivalabs.moviebuffs.core.exception.ApplicationException;
import com.sivalabs.moviebuffs.core.exception.BadRequestException;
import com.sivalabs.moviebuffs.core.exception.ResourceNotFoundException;
import com.sivalabs.moviebuffs.web.api.UserRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;

@Slf4j
@RestControllerAdvice(basePackageClasses = UserRestController.class)
public class GlobalExceptionHandler {

	private ExceptionTranslator translator = new ExceptionTranslator();

	@ExceptionHandler(value = ResourceNotFoundException.class)
	ResponseEntity<Problem> handleResourceNotFoundException(ResourceNotFoundException exception,
			NativeWebRequest request) {
		log.error(exception.getLocalizedMessage(), exception);
		return translator.create(Status.NOT_FOUND, exception, request);
	}

	@ExceptionHandler(value = ApplicationException.class)
	ResponseEntity<Problem> handleApplicationException(ApplicationException exception, NativeWebRequest request) {
		log.error(exception.getLocalizedMessage(), exception);
		return translator.create(Status.BAD_REQUEST, exception, request);
	}

	@ExceptionHandler(value = BadRequestException.class)
	ResponseEntity<Problem> handleBadRequestException(BadRequestException exception, NativeWebRequest request) {
		log.error(exception.getLocalizedMessage(), exception);
		return translator.create(Status.BAD_REQUEST, exception, request);
	}

}

class ExceptionTranslator implements ProblemHandling {

}
