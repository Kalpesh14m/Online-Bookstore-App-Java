package com.bridgelabz.bookstore.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long bookId;

	@Column(name = "book_name", nullable = false)
	private String bookName;

	@Column(name = "quantity", nullable = false)
	@Min(value = 0)
	private int quantity;

	@Column(name = "price", nullable = false)
	@Min(value = 0)
	private Double price;

	@Column(name = "author_name", nullable = false)
	private String authorName;

	@Column(name = "created_date_time", nullable = false)
	private LocalDateTime createdDateAndTime;

	@Column(name = "lastupdated_date_time")
	private LocalDateTime lastUpdatedDateAndTime;

	@Column(name = "verified_date_time")
	private LocalDateTime verifiedDateAndTime;

	@Column(name = "rejection_counts", columnDefinition = "int default 0")
	private int rejectionCounts;

	@Column(name = "image_URL", nullable = false)
	private String imageURL;

	@Column(name = "description", length = 1000, nullable = false)
	private String description;

	@Column(name = "is_approved", nullable = false, columnDefinition = "boolean default false")
	private boolean isApproved;

	@Column(name = "is_approval_sent", nullable = false, columnDefinition = "boolean default false")
	private boolean isApprovalSent;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "seller_id")
	private User seller;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "book_id")
	private List<Review> review;

	@JsonIgnore
	@OneToMany(mappedBy = "book",orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CartBooks> cartBooks;

	@JsonIgnore
	@ManyToMany(mappedBy = "books")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Order> orders;

	public List<Review> getReview() {
	return review;
	}

	public void setReview(List<Review> review) {
	this.review = review;
	}

	public Long getBookId() {
	return bookId;
	}

	public void setBookId(Long bookId) {
	this.bookId = bookId;
	}

	public String getBookName() {
	return bookName;
	}

	public void setBookName(String bookName) {
	this.bookName = bookName;
	}

	public int getQuantity() {
	return quantity;
	}

	public void setQuantity(int quantity) {
	this.quantity = quantity;
	}

	public Double getPrice() {
	return price;
	}

	public void setPrice(Double price) {
	this.price = price;
	}

	public String getAuthorName() {
	return authorName;
	}

	public void setAuthorName(String authorName) {
	this.authorName = authorName;
	}

	public LocalDateTime getCreatedDateAndTime() {
	return createdDateAndTime;
	}

	public void setCreatedDateAndTime(LocalDateTime createdDateAndTime) {
	this.createdDateAndTime = createdDateAndTime;
	}

	public LocalDateTime getLastUpdatedDateAndTime() {
	return lastUpdatedDateAndTime;
	}

	public void setLastUpdatedDateAndTime(LocalDateTime lastUpdatedDateAndTime) {
	this.lastUpdatedDateAndTime = lastUpdatedDateAndTime;
	}

	public LocalDateTime getVerifiedDateAndTime() {
	return verifiedDateAndTime;
	}

	public void setVerifiedDateAndTime(LocalDateTime verifiedDateAndTime) {
	this.verifiedDateAndTime = verifiedDateAndTime;
	}

	public int getRejectionCounts() {
	return rejectionCounts;
	}

	public void setRejectionCounts(int rejectionCounts) {
	this.rejectionCounts = rejectionCounts;
	}

	public String getImageURL() {
	return imageURL;
	}

	public void setImageURL(String imageURL) {
	this.imageURL = imageURL;
	}

	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	public boolean isApproved() {
	return isApproved;
	}

	public void setApproved(boolean isApproved) {
	this.isApproved = isApproved;
	}

	public boolean isApprovalSent() {
	return isApprovalSent;
	}

	public void setApprovalSent(boolean isApprovalSent) {
	this.isApprovalSent = isApprovalSent;
	}

	public User getSeller() {
	return seller;
	}

	public void setSeller(User seller) {
	this.seller = seller;
	}

	public List<CartBooks> getCartBooks() {
	return cartBooks;
	}

	public void setCartBooks(List<CartBooks> cartBooks) {
	this.cartBooks = cartBooks;
	}



	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", quantity=" + quantity + ", price=" + price
				+ ", authorName=" + authorName + ", createdDateAndTime=" + createdDateAndTime
				+ ", lastUpdatedDateAndTime=" + lastUpdatedDateAndTime + ", verifiedDateAndTime=" + verifiedDateAndTime
				+ ", rejectionCounts=" + rejectionCounts + ", imageURL=" + imageURL + ", description=" + description
				+ ", isApproved=" + isApproved + ", isApprovalSent=" + isApprovalSent + ", seller=" + seller
				+ ", review=" + review + ", cartBooks=" + cartBooks + ", orders=" + orders + "]";
	}
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}