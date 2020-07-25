package com.bridgelabz.bookstore.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cart_book")
public class CartBooks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_book_id")
	private long cartBookId;

	@Column(name = "book_quantity", nullable = false, columnDefinition = "int default 1")
	@Min(value = 1)
	private int bookQuantity;

	@Column(name = "total_price", nullable = false)
	@Min(value = 0)
	private Double totalBookPrice;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "book_id")
	private Book book;

	public long getCartBookId() {
		return cartBookId;
	}

	public void setCartBookId(long cartBookId) {
		this.cartBookId = cartBookId;
	}

	public int getBookQuantity() {
		return bookQuantity;
	}

	public void setBookQuantity(int bookQuantity) {
		this.bookQuantity = bookQuantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Double getTotalBookPrice() {
		return totalBookPrice;
	}

	public void setTotalBookPrice(Double totalBookPrice) {
		this.totalBookPrice = totalBookPrice;
	}
	
}
