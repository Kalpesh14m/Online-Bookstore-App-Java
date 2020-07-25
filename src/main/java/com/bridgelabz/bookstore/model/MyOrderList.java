package com.bridgelabz.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "myorderlist")
public class MyOrderList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "myorder_id")
	private long myOrderId;

	@Column(name = "qunatity")
	private int qunatity;

	@Column(name = "vendername")
	private String venderName;

	@Column(name = "bookname")
	private String bookName;

	@Column(name = "review")
	private int review;

	@Column(name = "totel_price")
	private double totelPrice;

	@ManyToOne
	@JoinColumn(name = "bookId")
	private Book book;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User buyer;

	public long getMyOrderId() {
		return myOrderId;
	}

	public void setMyOrderId(long myOrderId) {
		this.myOrderId = myOrderId;
	}

	public int getQunatity() {
		return qunatity;
	}

	public void setQunatity(int qunatity) {
		this.qunatity = qunatity;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getReview() {
		return review;
	}

	public void setReview(int review) {
		this.review = review;
	}

	public double getTotelPrice() {
		return totelPrice;
	}

	public void setTotelPrice(double totelPrice) {
		this.totelPrice = totelPrice;
	}

}
