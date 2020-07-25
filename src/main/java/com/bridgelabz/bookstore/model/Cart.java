package com.bridgelabz.bookstore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private long cartId;

	@Column(name = "total_items_in_cart", nullable = false, columnDefinition = "int default 0")
	@Min(value = 0, message = "Total books in cart should be greater than 0")
	@Max(value = 5, message = "Total books in cart should be less than 5")
	private int totalBooksInCart;

	@OneToMany(mappedBy = "cart")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CartBooks> cartBooks;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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
