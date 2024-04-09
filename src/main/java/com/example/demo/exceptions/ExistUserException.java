package com.example.demo.exceptions;

public class ExistUserException extends RuntimeException {
	private static final long serialVersionUID = 6804682162517922887L;

	public ExistUserException(String msg) {
		super(msg);
	}
	
}
