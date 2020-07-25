package com.bridgelabz.bookstore.exception;

public class CartItemsLimitException extends RuntimeException {

	public CartItemsLimitException(String msg) {
		super(msg);
	}
}
