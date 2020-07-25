package com.bridgelabz.bookstore.exception;
public class MailSendingException extends Exception {

	private static final long serialVersionUID = 1L;
	private int status;
	private String message;

	public MailSendingException() {
		super();
	}

	public MailSendingException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public MailSendingException(String message, int status) {
		super(message);
		this.status = status;
		this.message = message;
	}

}