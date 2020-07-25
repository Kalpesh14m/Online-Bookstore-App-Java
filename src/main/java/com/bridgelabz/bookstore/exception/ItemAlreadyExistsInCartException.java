package com.bridgelabz.bookstore.exception;

public class ItemAlreadyExistsInCartException extends RuntimeException {

	public ItemAlreadyExistsInCartException(String msg) {
		super(msg);
	}
}
