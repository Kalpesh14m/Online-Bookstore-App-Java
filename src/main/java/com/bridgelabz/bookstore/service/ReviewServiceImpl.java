package com.bridgelabz.bookstore.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.ReviewConstants;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Review;
import com.bridgelabz.bookstore.model.ReviewApp;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.ReviewDTO;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.OrderRepo;
import com.bridgelabz.bookstore.repo.ReviewRepository;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.JwtValidate;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private UserRepo userRepository;

	@Autowired
	BookRepo bookRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	private OrderRepo orderRepository;


	


	/**
	 * Method to add rating for a particular book
	 * 
	 * @param token, reviewDTO, bookId
	 * @return Review 
	 * @throws - UserNotFound => if user credentials mismatch 
	 *           BookNotFound => if book is not present
	 */
	public Review addRating(String token, long bookId, ReviewDTO reviewDTO,long myorderid) {

		User user = userRepository.getUserById(Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId")))
				.orElseThrow(() -> new UserNotFoundException(ReviewConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE,
						ReviewConstants.NOT_FOUND_RESPONSE_CODE));
		Book book = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new UserNotFoundException(ReviewConstants.BOOK_NOT_FOUND,
						ReviewConstants.NOT_FOUND_RESPONSE_CODE));
		if (getReview(token, bookId) == null) {
			Review review = new Review();
			BeanUtils.copyProperties(reviewDTO, review);
			user.getReview().add(review);
			userRepository.addUser(user);
			book.getReview().add(review);
			bookRepository.save(book);
			orderRepository.addReview(myorderid,reviewDTO.getRating());
			return review;
		} else {
			Review review = getReview(token, bookId);
			BeanUtils.copyProperties(reviewDTO, review);
			reviewRepository.update(review);

			orderRepository.addReview(myorderid,reviewDTO.getRating());
			return review;
		}
	}

	@Override
	public Review getReview(String token, long bookId) {
		User user = userRepository.getUserById(Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId")))
				.orElseThrow(() -> new UserNotFoundException(ReviewConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE,
						ReviewConstants.NOT_FOUND_RESPONSE_CODE));
		Book book = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new UserNotFoundException(ReviewConstants.BOOK_NOT_FOUND,
						ReviewConstants.NOT_FOUND_RESPONSE_CODE));
		List<Review> userReview = user.getReview();
		List<Review> bookReview = book.getReview();
		for (int i = 0; i < userReview.size(); i++) {
			for (int j = 0; j < bookReview.size(); j++) {
				if (userReview.get(i).getReviewId() == bookReview.get(j).getReviewId())
					return userReview.get(i);
			}
		}
		return null;
	}

	@Override
	public ReviewApp addRatingApp(String token, ReviewDTO reviewDTO) {

		User user = userRepository.getUserById(Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId")))
				.orElseThrow(() -> new UserNotFoundException(ReviewConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE,
						ReviewConstants.NOT_FOUND_RESPONSE_CODE));
		ReviewApp review = new ReviewApp();
		BeanUtils.copyProperties(reviewDTO, review);
		user.getReviewApp().add(review);
		userRepository.addUser(user);
		return review;
	}

}
