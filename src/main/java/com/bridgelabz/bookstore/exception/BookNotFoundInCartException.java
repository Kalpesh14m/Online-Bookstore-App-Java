package com.bridgelabz.bookstore.exception;

public class BookNotFoundInCartException extends RuntimeException {

	public BookNotFoundInCartException(String msg) {
		super(msg);
	}
}
