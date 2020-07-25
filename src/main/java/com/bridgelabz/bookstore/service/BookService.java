package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.dto.BookDto;

public interface BookService {

	public List<Book> findBookByAuthorNameAndTile(String text);

	public List<Book> findAllBook();

	public void addBook(BookDto request, Long userId);

	public List<Book> sortBookByAsc();

	public List<Book> sortBookByDesc();

	public List<Book> findBookCount();
	
	public List<Book> findBookByPage( Integer pageNo);

}
