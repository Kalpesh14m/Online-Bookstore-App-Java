package com.bridgelabz.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.ReviewConstants;
import com.bridgelabz.bookstore.model.dto.ReviewDTO;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.ReviewService;

@RestController
public class ReviewController {

	@Autowired 
	private ReviewService reviewService;
	
	@PostMapping("/review/{bookId}/{myOrderId}")
	public ResponseEntity<Response> addReview(@PathVariable("bookId") long bookId,@PathVariable("myOrderId") long myOrderId,@RequestHeader("token") String token, @RequestBody ReviewDTO reviewDTO) {
		reviewService.addRating(token,bookId, reviewDTO,myOrderId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(ReviewConstants.REVIEW_ADDED_SUCCESSFULLY, HttpStatus.OK.value(), reviewDTO));
	}
	
	@GetMapping("/review/{bookId}")
	public ResponseEntity<Response> getReview(@PathVariable("bookId") long bookId,@RequestHeader("token") String token){		
		return ResponseEntity.status(HttpStatus.OK).body(new Response(ReviewConstants.REVIEW_FOUND, HttpStatus.OK.value(), reviewService.getReview(token, bookId)));
	}
	
	@PostMapping("/reviewApp")
	public ResponseEntity<Response> addReviewApp(@RequestHeader("token") String token, @RequestBody ReviewDTO reviewDTO) {
		reviewService.addRatingApp(token, reviewDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(ReviewConstants.REVIEW_ADDED_SUCCESSFULLY, HttpStatus.OK.value(), reviewDTO));
	}
}
