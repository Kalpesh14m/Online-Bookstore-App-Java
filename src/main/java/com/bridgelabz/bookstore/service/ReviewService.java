package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.model.Review;
import com.bridgelabz.bookstore.model.ReviewApp;
import com.bridgelabz.bookstore.model.dto.ReviewDTO;

public interface ReviewService {

	public Review addRating(String token, long bookId, ReviewDTO reviewDTO,long myorderid);

	public Review getReview(String token, long bookId);

	public ReviewApp addRatingApp(String token, ReviewDTO reviewDTO);

}
