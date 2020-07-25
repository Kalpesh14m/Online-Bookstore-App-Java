package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookservice;

	

	@GetMapping(value = "/getBooks")
	public ResponseEntity<Response> getAllBooks() {
		List<Book> getAllBooks = bookservice.findAllBook();
		if (!getAllBooks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Books Found", HttpStatus.OK.value(), getAllBooks));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("No Books Found", HttpStatus.NOT_FOUND.value()));
	}

	@GetMapping("/getBooksByPriceAsc")
	public ResponseEntity<Response> sortBookByPriceAsc() {
		List<Book> sortBookByPriceAsc = bookservice.sortBookByAsc();
		if (!sortBookByPriceAsc.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Books Found", HttpStatus.OK.value(), sortBookByPriceAsc));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("No Books Found", HttpStatus.NOT_FOUND.value()));
	}

	@GetMapping("/getBooksByPriceDesc")
	public ResponseEntity<Response> sortBookByPriceDesc() {
		List<Book> sortBookByPriceDesc = bookservice.sortBookByDesc();
		if (!sortBookByPriceDesc.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Books Found", HttpStatus.OK.value(), sortBookByPriceDesc));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("No Books Found", HttpStatus.NOT_FOUND.value()));
	}

	@GetMapping(value = "/bookStoreApplication/getBookByAuthorName")
	public ResponseEntity<Response> searchBookByAuthorName(@RequestParam("authorName") String text) {
		List<Book> searchBookByAuthorName = bookservice.findBookByAuthorNameAndTile(text);
		if (!searchBookByAuthorName.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Books Found", HttpStatus.OK.value(), searchBookByAuthorName));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("No Books Found", HttpStatus.NOT_FOUND.value()));
	}

	@GetMapping(value = "/getBookCount")
	public ResponseEntity<Response> findBookCount() {
		List<Book> getBookCount = bookservice.findBookCount();
		if (getBookCount != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Books Found", HttpStatus.OK.value(), getBookCount));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("No Books Found", HttpStatus.NOT_FOUND.value()));

	}
	
	

	@GetMapping(value = "/getBookByPage")
	public ResponseEntity<Response> getBookByPage(@RequestParam(defaultValue = "0") Integer pageNo) {
		System.out.println(pageNo);
		List<Book> getAllBooks = bookservice.findBookByPage(pageNo);
		if (!getAllBooks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Books Found", HttpStatus.OK.value(), getAllBooks));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("No Books Found", HttpStatus.NOT_FOUND.value()));
	}

}
