package com.bridgelabz.bookstore.repo;

import java.util.Optional;

import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.CartBooks;

public interface CartRepo {

	boolean saveToCart(Cart cart);
	
	boolean saveToCartBooks(CartBooks cartBooks);
	
	Optional<Cart> findByUserId(Long id);

	boolean removeByCartBookId(Long cartBookId);

	boolean deleteByCartId(long cartId);
}
