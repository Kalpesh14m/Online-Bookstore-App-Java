package com.bridgelabz.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.Wishlist;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.WishlistRepo;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class WishlistServiceImp implements WishlistService {

	@Autowired
	private BookRepo bookRepo;
	@Autowired
	private WishlistRepo wishlistRepo;

	@Autowired
	private TokenUtility tokenUtility;
	
	@Transactional
	public Wishlist addtoWishlist(String token, long bookId) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		Book book = bookRepo.getBookById(bookId).orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		Wishlist wishlist = Optional.ofNullable(buyer.getUserWishlist()).orElse(new Wishlist());
		List<Book> booksInWishlist = Optional.ofNullable(wishlist.getBooks()).orElse(new ArrayList<>());
		wishlist.setUser(buyer);
		booksInWishlist.add(book);
		wishlist.setBooks(booksInWishlist);
		buyer.setUserWishlist(wishlist);
		wishlistRepo.saveToWishlist(wishlist);
		return wishlist;
	}

	@Override
	public boolean removeBookFromWishlist(String token, long bookId) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		List<Book> booksInWishlist = buyer.getUserWishlist().getBooks();
		Book bookToBeRemoved = booksInWishlist.stream().filter(books -> books.getBookId() == bookId).findAny()
				.orElseThrow(() -> {
					throw new BookNotFoundException(Constant.BOOK_NOT_FOUND);
				});
		booksInWishlist.remove(bookToBeRemoved);
		buyer.getUserWishlist().setBooks(booksInWishlist);
		return wishlistRepo.saveToWishlist(buyer.getUserWishlist());

	}

	@Override
	public List<Book> displayItems(String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		return buyer.getUserWishlist().getBooks();
	}

	
	}
