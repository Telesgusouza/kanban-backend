package com.example.demo.exceptions;

public class UserDoesNotExistException extends RuntimeException {
	private static final long serialVersionUID = 93360754382550808L;

	public UserDoesNotExistException(String msg) {
		super(msg);
	}

}
