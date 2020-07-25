package com.bridgelabz.bookstore.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

	public UserAlreadyRegisteredException(String msg) {
		super(msg);
	}
}
