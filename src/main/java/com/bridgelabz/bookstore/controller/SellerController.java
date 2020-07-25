package com.bridgelabz.bookstore.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.model.dto.UpdateBookDto;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.SellerService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/sellers")
@Api(value = "Seller Controller to perform CRUD operations on book")
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private Response response;

	@PostMapping(value = "/addBook")
	public ResponseEntity<Response> addBook(@RequestBody @Valid BookDto newBook, BindingResult result,
			@RequestHeader("token") String token) {
		if (result.hasErrors()) {
			response.setMessage(result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<>(new Response(response.getMessage(), HttpStatus.NOT_ACCEPTABLE.value()),
					HttpStatus.NOT_ACCEPTABLE);
		}
		Book addedbook = sellerService.addBook(newBook, token);
		if (addedbook != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response(
					Constant.BOOK_ADDITION_SUCCESSFULL_MESSAGE, Constant.CREATED_RESPONSE_CODE, addedbook));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.BOOK_ADDITION_FAILED_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE));

	}

	@PutMapping(value = "/updateBook/{bookId}", headers = "Accept=application/json")
	public ResponseEntity<Response> updateBook(@RequestBody @Valid UpdateBookDto updatedBookInfo, BindingResult result,
			@PathVariable long bookId, @RequestHeader("token") String token) {
		if (result.hasErrors()) {
			response.setMessage(result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<>(new Response(response.getMessage(), HttpStatus.NOT_ACCEPTABLE.value()),
					HttpStatus.NOT_ACCEPTABLE);
		}
		Book updatedBook = sellerService.updateBook(updatedBookInfo, bookId, token);
		if (updatedBook != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(
					Constant.BOOK_UPDATION_SUCCESSFULL_MESSAGE, Constant.ACCEPT_RESPONSE_CODE, updatedBook));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new Response(Constant.BOOK_UPDATION_FAILED_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE, updatedBook));
	}

	@GetMapping(value = "/displayBooks")
	public ResponseEntity<Response> displayBooks(@RequestHeader("token") String token,@RequestParam("pageNo") Integer pageNo) {
		List<Book> sellerBooks = sellerService.getAllBooks(token,pageNo);
		if (!sellerBooks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.DISPLAYING_BOOKS, Constant.OK_RESPONSE_CODE, sellerBooks));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(Constant.BOOK_NOT_FOUND, Constant.NOT_FOUND_RESPONSE_CODE));
	}

	@DeleteMapping(value = "/removeBook/{bookId}")
	public ResponseEntity<Response> removeBook(@PathVariable("bookId") long bookId,
			@RequestHeader("token") String token) {
		boolean isDeleted = sellerService.removeBook(bookId, token);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_DELETION_SUCCESSFULL_MESSAGE, Constant.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(Constant.BOOK_NOT_FOUND, Constant.NOT_FOUND_RESPONSE_CODE));
	}

	@GetMapping("/search/{input}")
	public ResponseEntity<Response> searchNotes(@RequestHeader(value = "token") String token,
			@PathVariable String input) throws IOException {

		List<Book> books = sellerService.searchBook(token, input);
		if (books.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Response(Constant.BOOK_NOT_FOUND, Constant.NOT_FOUND_RESPONSE_CODE));

		return  ResponseEntity.status(HttpStatus.OK).body(new Response(Constant.BOOK_FOUND,Constant.OK_RESPONSE_CODE,books));
	}

	@PutMapping("/approvalSent/{bookId}")
	public ResponseEntity<Response> sentForApproval(@RequestHeader("token") String token,
			@PathVariable("bookId") long bookId) {
		if (sellerService.sentForApproval(bookId, token)) {
			return new ResponseEntity<>(new Response("Book sent For Approval Success", HttpStatus.OK.value()),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response("Book sent For Approval Failed", HttpStatus.ALREADY_REPORTED.value()),
				HttpStatus.ALREADY_REPORTED);
	}
	
	@GetMapping("/booksCount")
	public ResponseEntity<Response> booksCount(@RequestHeader("token") String token) {
		long booksCount = sellerService.booksCount(token);
		if (booksCount>=0) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Books Count fetched succesfully", Constant.OK_RESPONSE_CODE, booksCount));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("Books Count fetched failed", Constant.NOT_FOUND_RESPONSE_CODE));
	} 
}
