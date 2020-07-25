package com.bridgelabz.bookstore.exception;

public class UserException extends Exception {
	private static final long serialVersionUID = 1L;
	private int statusCode;
	String message;

	public UserException(String message) {
		super(message);
		this.message = message;
	}

	public UserException(String message, int status) {
		super(message);
		this.message = message;
		this.statusCode = status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(int status) {
		this.statusCode = status;
	}

	public int getStatus() {
		return statusCode;
	}

}