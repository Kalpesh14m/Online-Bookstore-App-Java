package com.bridgelabz.bookstore.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.bookstore.response.Response;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getMessage(),
				HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getMessage(),
				HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);

	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> handleUserException(UserException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Response> handleUserNotFoundException(UserNotFoundException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(UserAlreadyRegisteredException.class)
	public ResponseEntity<Response> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.ALREADY_REPORTED.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.ALREADY_REPORTED);
	}

	@ExceptionHandler(UserAuthorizationException.class)
	public ResponseEntity<Response> handleUserAuthorizationException(UserAuthorizationException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.UNAUTHORIZED.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.ALREADY_REPORTED);
	}

	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<Response> handleTokenNotFoundException(TokenNotFoundException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BookAlreadyExistsException.class)
	public ResponseEntity<Response> handleBookAlreadyExistsException(BookAlreadyExistsException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.ALREADY_REPORTED.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.ALREADY_REPORTED);
	}

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<Response> handleBookNotFoundException(BookNotFoundException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.ALREADY_REPORTED.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.ALREADY_REPORTED);
	}

	@ExceptionHandler(BookQuantityException.class)
	public ResponseEntity<Response> handleBookQuantityException(BookQuantityException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.ALREADY_REPORTED);
	}

	@ExceptionHandler(BookException.class)
	public ResponseEntity<Response> handleBookException(BookException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ItemAlreadyExistsInCartException.class)
	public ResponseEntity<Response> handleItemAlreadyExistsInCartException(ItemAlreadyExistsInCartException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.ALREADY_REPORTED.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.ALREADY_REPORTED);
	}

	@ExceptionHandler(CartItemsLimitException.class)
	public ResponseEntity<Response> handleCarItemsLimitExceededException(CartItemsLimitException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.EXPECTATION_FAILED.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(BookNotFoundInCartException.class)
	public ResponseEntity<Response> handleBookNotFoundInCartException(BookNotFoundInCartException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.NOT_FOUND.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BookOutOfStockException.class)
	public ResponseEntity<Response> handleBookOutOfStockException(BookOutOfStockException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.EXPECTATION_FAILED.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(CartException.class)
	public ResponseEntity<Response> handleCartException(CartException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), ex.getLocalizedMessage(),
				HttpStatus.EXPECTATION_FAILED.value());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.EXPECTATION_FAILED);
	}
}