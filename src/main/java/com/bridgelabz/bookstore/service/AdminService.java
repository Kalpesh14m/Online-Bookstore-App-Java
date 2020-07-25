package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.User;

public interface AdminService {
	

	public List<User> getSellers(String token);	
	public List<Book> getBooksForVerification(long sellerId, String token);
	public void bookVerification(Long bookId, Long sellerId, boolean verify, String token, String feedback) throws BookException;

	
}
