package com.bridgelabz.bookstore.model.dto;

public class ReviewDTO {

	private int rating;
	private String review;
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
	public ReviewDTO() {
		super();
	}
	public ReviewDTO(int rating, String review) {
		super();
		this.rating = rating;
		this.review = review;
	}
}
