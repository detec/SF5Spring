package org.openbox.sf5.json;

import java.io.IOException;
import java.sql.SQLException;

import org.openbox.sf5.json.exceptions.ApiError;
import org.openbox.sf5.json.exceptions.ItemNotFoundException;
import org.openbox.sf5.json.exceptions.NotAuthenticatedException;
import org.openbox.sf5.json.exceptions.UsersDoNotCoincideException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@EnableWebMvc
@ControllerAdvice(basePackages = { "org.openbox.sf5.json.endpoints", "org.openbox.sf5.json" })
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	// if item exists when creating
	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ResponseEntity<ApiError> handleIdException(IllegalArgumentException ex, WebRequest request) {
		return constructSerializedException(ex, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = { IllegalStateException.class, IOException.class, SQLException.class })
	public ResponseEntity<ApiError> handleServerException(Exception ex, WebRequest request) {
		return constructSerializedException(ex, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	protected ResponseEntity<ApiError> constructSerializedException(Exception ex, HttpStatus status) {
		ApiError apiError = new ApiError(status, ex);
        logger.info("Exception caught : " + ex.getClass().getSimpleName());
		return new ResponseEntity<>(apiError, status);
	}

	protected ResponseEntity<Object> constructSerializedException(Exception ex, HttpStatus status,
			String customMessage) {

		ApiError apiError = new ApiError(status, ex, customMessage);
		logger.info("Exception caught : " + ex.getClass().getSimpleName());
		return new ResponseEntity<>(apiError, status);
	}

	@ExceptionHandler(NotAuthenticatedException.class)
	public ResponseEntity<ApiError> handleNotAuthenticatedException(NotAuthenticatedException ex, WebRequest request) {

		return constructSerializedException(ex, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(value = { UsersDoNotCoincideException.class })
	public ResponseEntity<ApiError> handleDifferentUser(UsersDoNotCoincideException ex, WebRequest request) {
		return constructSerializedException(ex, HttpStatus.NOT_ACCEPTABLE);

	}

	@ExceptionHandler(value = { ItemNotFoundException.class })
	public ResponseEntity<ApiError> handleUserNotFound(Exception ex, WebRequest request) {

		return constructSerializedException(ex, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "No handler found for " + ex.getMethod() + " " + request.getContextPath();

		return constructSerializedException(ex, HttpStatus.NOT_FOUND, error);
	}

	/**
	 * Default error handler for other unforeseen exceptions.
	 *
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ApiError> handleAll(Exception ex, WebRequest request) {

		logger.warn("Caught an unexpected error: " + ex.getMessage(), ex);

		return constructSerializedException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		logger.warn("Returning HTTP 400 Bad Request", ex);
		return handleExceptionInternal(ex, ex.getMessage(), headers, HttpStatus.ACCEPTED, request);

	}
}
