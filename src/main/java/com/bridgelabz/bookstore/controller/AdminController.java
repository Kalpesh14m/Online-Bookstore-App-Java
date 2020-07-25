package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.AdminConstants;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/getSellersForVerification")
	public ResponseEntity<Response> getSellersForVerification(@RequestHeader("token") String token) {

		List<User> sellers = adminService.getSellers(token);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(AdminConstants.USER_FOUND, HttpStatus.OK.value(), sellers));
	}

	@GetMapping("/getBooksForVerification/{sellerId}")
	public ResponseEntity<Response> getBooksForVerification(@PathVariable("sellerId") long sellerId,
			@RequestHeader("token") String token) {
		List<Book> books = adminService.getBooksForVerification(sellerId, token);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(AdminConstants.BOOK_FOUND, HttpStatus.OK.value(), books));
	}

	@PutMapping("/bookVerification/{bookId}/{sellerId}/{verify}")
	
	public ResponseEntity<Response> bookVerification(@PathVariable("bookId") Long bookId,
			@PathVariable("sellerId") Long sellerId, @PathVariable("verify") boolean verify,
			@RequestHeader("token") String token,
			@RequestBody(required = false) String feedback) throws BookException {

		adminService.bookVerification(bookId, sellerId, verify, token,feedback);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(AdminConstants.BOOK_VERIFIED_SUCCESSFULLY_MEAASGE, HttpStatus.OK.value()));

	}
}
