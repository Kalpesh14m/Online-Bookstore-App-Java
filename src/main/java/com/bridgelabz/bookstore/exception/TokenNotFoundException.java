package com.bridgelabz.bookstore.exception;
public class TokenNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	private String message;
	private int status;

	public TokenNotFoundException(String message) {
		super(message);
		this.message = message;

	}

	public TokenNotFoundException(String message, int status) {
		super(message);
		this.status = status;
		this.message = message;
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