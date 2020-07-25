package com.bridgelabz.bookstore.exception;

public class BookOutOfStockException extends RuntimeException {

	public BookOutOfStockException(String msg) {
		super(msg);
	}
}
