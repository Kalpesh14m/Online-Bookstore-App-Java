package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Wishlist;


public interface WishlistService {
  Wishlist addtoWishlist(String token, long bookId);
	boolean removeBookFromWishlist(String token, long bookId);
	List<Book> displayItems(String token);

}
