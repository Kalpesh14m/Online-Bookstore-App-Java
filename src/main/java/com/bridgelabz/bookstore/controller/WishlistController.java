package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Wishlist;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.WishlistService;

@RestController

@RequestMapping(value = "/wishlists")

public class WishlistController {
	@Autowired
	WishlistService wishlistService;
	
	@PostMapping(value = "/addToWishlist/{bookId}", headers = "Accept=application/json")
	public ResponseEntity<Response> addtoWishlist(@RequestHeader("token") String token,
			@PathVariable("bookId") long bookId) {
		Wishlist wishlist = wishlistService.addtoWishlist(token, bookId);
		if (wishlist!=null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_ADD_TO_WISHLIST, Constant.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.BOOK_ADD_TO_WISHLIST_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

	@GetMapping("/displayItems")
	public ResponseEntity<Response> displayItems(@RequestHeader("token") String token){
		List<Book> books = wishlistService.displayItems(token);
		if(!books.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOKS_DISPLAY_MESSAGE, Constant.OK_RESPONSE_CODE,books));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.BOOKS_DISPLAY_FAILED_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE));
	}
	
	@DeleteMapping("/removeFromWishlist/{bookId}")
	public ResponseEntity<Response> removeFromWishlist(@RequestHeader String token, @PathVariable long bookId) {
		boolean status =wishlistService.removeBookFromWishlist(token, bookId);
		if (status) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_REMOVED_FROM_WISHLIST, Constant.OK_RESPONSE_CODE));
		} else
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new Response(Constant.BOOK_REMOVAL_FROM_WISHLIST_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
	}


}

