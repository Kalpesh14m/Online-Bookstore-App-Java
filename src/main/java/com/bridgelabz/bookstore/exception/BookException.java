package com.bridgelabz.bookstore.exception;

public class BookException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
	private int status;

	public BookException() {
		super();
	}

	public BookException(String message, int status) {
		super();
		this.message = message;
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
