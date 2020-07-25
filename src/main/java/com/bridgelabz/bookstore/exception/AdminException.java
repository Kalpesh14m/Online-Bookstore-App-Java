package com.bridgelabz.bookstore.exception;

public class AdminException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int statusCode;
	String message;


	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AdminException(String message) {
		super();
		this.message = message;
	}

	public AdminException(String message, int status) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

}
