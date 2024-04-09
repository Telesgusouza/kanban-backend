package com.example.demo.controller.exceptions;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exceptions.ExistUserException;
import com.example.demo.exceptions.UserDoesNotExistException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> resourceNotFound(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		String error = "Resource not found";
		Integer statusCode = 422;
		StandardError err = new StandardError(Instant.now(), statusCode, error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(statusCode).body(err);
	}

	@ExceptionHandler(ExistUserException.class)
	public ResponseEntity<StandardError> existUserException(ExistUserException e, HttpServletRequest request) {
		String error = "username is in use";
		Integer statusCode = 409;
		StandardError err = new StandardError(Instant.now(), statusCode, error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(statusCode).body(err);
	}

	//
	@ExceptionHandler(UserDoesNotExistException.class)
	public ResponseEntity<StandardError> userDoesNotExistException(UserDoesNotExistException e,
			HttpServletRequest request) {
		String error = "Email does not exist";
		Integer statusCode = 400;
		StandardError err = new StandardError(Instant.now(), statusCode, error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(statusCode).body(err);
	}
}
