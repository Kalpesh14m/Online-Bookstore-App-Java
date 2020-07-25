package com.bridgelabz.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReviewApp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long reviewAppId;

	public ReviewApp() {
		super();

	}

	@Column
	private int rating;

	public long getReviewAppId() {
		return reviewAppId;
	}

	public void setReviewAppId(long reviewAppId) {
		this.reviewAppId = reviewAppId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Column
	private String review;
}
