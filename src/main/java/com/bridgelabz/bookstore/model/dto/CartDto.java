package com.bridgelabz.bookstore.model.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.bridgelabz.bookstore.model.CartBooks;

public class CartDto {

	@Min(value = 0,message = "Cart Size must be greater than Zero")
	@Max(value = 5,message = "Cart Size must be less than Six")
	private int totalBooksInCart;
	
	private List<CartBooks> cartBooks;

	public int getTotalBooksInCart() {
		return totalBooksInCart;
	}

	public void setTotalBooksInCart(int totalBooksInCart) {
		this.totalBooksInCart = totalBooksInCart;
	}

	public List<CartBooks> getCartBooks() {
		return cartBooks;
	}

	public void setCartBooks(List<CartBooks> cartBooks) {
		this.cartBooks = cartBooks;
	}

}
