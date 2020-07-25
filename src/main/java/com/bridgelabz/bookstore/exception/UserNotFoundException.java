package com.bridgelabz.bookstore.exception;
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int status;
	String message;

	public UserNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public UserNotFoundException(String message, int status) {
		super(message);
		this.message = message;
		this.status = status;
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

	public int getStatus() {
		return status;
	}
	

}