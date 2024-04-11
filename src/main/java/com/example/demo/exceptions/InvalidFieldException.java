package com.example.demo.exceptions;

public class InvalidFieldException extends RuntimeException {
	private static final long serialVersionUID = 4816043463147666066L;

	public InvalidFieldException(String msg) {
		super(msg);
	}
	
}
