package com.bridgelabz.bookstore.repo;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.bookstore.model.Book;

public interface BookRepo {

	public List<Book> findBookByAuthorName(String authorName);

	public List<Book> findBookByTitle(String bookName);

	public List<Book> findAllBooks();

	public List<Book> getBooksForVerification();

	public Optional<Book> getBookById(Long bookId);

	public void deleteBook(Book book);

	public void save(Book book);

	public List<Book> sortBookAsc();

	public List<Book> sortBookDesc();

	public List<Book> findBySellerId(Long id,Integer pageNo);

	public Book findByBookId(Long bookId);

	public List<Book> findBookCount();
	
	
	public List<Book> findBookByPage(Integer pageNo);

	public long findBookCount(Long id);
}
