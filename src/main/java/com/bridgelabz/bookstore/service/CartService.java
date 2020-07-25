package com.bridgelabz.bookstore.service;

import javax.validation.Valid;

import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.dto.CartDto;

public interface CartService {

	Cart addtocart(String token, long bookId);

	Cart removeBookFromCart(String token, long cartBookId);

	Cart displayItems(String token);

	Cart addQuantity(long cartBookId, String token);

	Cart removeQuantity(long cartBookId, String token);

	boolean placeOrder(@Valid CartDto cart, String token);

	Cart updateQuantity(long cartBookId,int quantity,String token);

	int getCartCount(String token);

}
