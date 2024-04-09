package com.example.demo.controller.exceptions;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exceptions.ExistUserException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> resourceNotFound(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		String error = "Resource not found";
		StandardError err = new StandardError(Instant.now(), 422, error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(422).body(err);
	}

	@ExceptionHandler(ExistUserException.class)
	public ResponseEntity<StandardError> resourceNotFound(ExistUserException e, HttpServletRequest request) {
		String error = "username is in use";
		StandardError err = new StandardError(Instant.now(), 422, error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(422).body(err);
	}
}
