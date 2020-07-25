package com.bridgelabz.bookstore.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.dto.CartDto;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.CartService;

@RestController
@RequestMapping(value = "/carts")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private Response response;

	@PostMapping(value = "/addToCart/{bookId}", headers = "Accept=application/json")
	public ResponseEntity<Response> addtocart(@RequestHeader("token") String token,
			@PathVariable("bookId") long bookId) {
		Cart cart = cartService.addtocart(token, bookId);
		if (cart != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_ADD_TO_CART, Constant.OK_RESPONSE_CODE, cart));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.BOOK_ADD_TO_CART_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

	@GetMapping("/displayItems")
	public ResponseEntity<Response> displayItems(@RequestHeader("token") String token) {
		Cart cart = cartService.displayItems(token);
		if (cart!=null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOKS_DISPLAYING_MESSAGE, Constant.OK_RESPONSE_CODE, cart));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.BOOKS_DISPLAYING_FAILED_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

	@PutMapping("/addQuantity/{cartBookId}")
	public ResponseEntity<Response> addQuantity(@RequestHeader("token") String token,
			@PathVariable("cartBookId") long cartBookId) {
		Cart cart = cartService.addQuantity(cartBookId, token);
		if (cart != null) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new Response(Constant.QUANTITY_INCREASED_SUCCESS_MESSAGE, Constant.OK_RESPONSE_CODE, cart));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(Constant.QUANTITY_INCREASED_FAILED_MESSAGE, Constant.OK_RESPONSE_CODE));
	}

	@PutMapping("/removeQuantity/{cartBookId}")
	public ResponseEntity<Response> removeQuantity(@RequestHeader("token") String token,
			@PathVariable("cartBookId") long cartBookId) {
		Cart cart = cartService.removeQuantity(cartBookId, token);
		if (cart != null) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new Response(Constant.QUANTITY_DECREASED_SUCCESS_MESSAGE, Constant.OK_RESPONSE_CODE, cart));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(Constant.QUANTITY_DECREASED_FAILED_MESSAGE, Constant.OK_RESPONSE_CODE));
	}

	@DeleteMapping("/removeFromCart/{cartBookId}")
	public ResponseEntity<Response> removeFromCart(@RequestHeader String token, @PathVariable long cartBookId) {
		Cart cart = cartService.removeBookFromCart(token, cartBookId);
		if (cart!=null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_REMOVED_FROM_CART, Constant.OK_RESPONSE_CODE,cart));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new Response(Constant.BOOK_REMOVAL_FROM_CART_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
		}
	}
	
	@PostMapping("/placeOrder")
	public ResponseEntity<Response> placeOrder(@RequestBody @Valid CartDto cart,BindingResult result,@RequestHeader("token") String token){
		if(result.hasErrors()) {
			response.setMessage(result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<>(new Response(response.getMessage(), HttpStatus.NOT_ACCEPTABLE.value()),
					HttpStatus.NOT_ACCEPTABLE);
		}
		boolean status = cartService.placeOrder(cart,token);
		if(status) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.PLACE_ORDER_SUCCESSFUL_MESSAGE, Constant.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(new Response(Constant.PLACE_ORDER_FAILED_MESSAGE, Constant.EXPECTATION_FAILED_RESPONSE_CODE));
	}
	
	@PutMapping("/updateQuantity/{cartBookId}/{quantity}")
	public ResponseEntity<Response> updateQuantity(@RequestHeader("token") String token,@PathVariable long cartBookId,@PathVariable int quantity){
		Cart cart = cartService.updateQuantity(cartBookId,quantity,token);
		if (cart!=null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.QUANTITY_UPDATION_SUCCESS_MESSAGE, Constant.OK_RESPONSE_CODE,cart));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new Response(Constant.QUANTITY_UPDATION_FAILED_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE));
		}
	}
	
	@GetMapping("/cartSize")
	public ResponseEntity<Response> getCartSize(@RequestHeader String token){
		int cartSize = cartService.getCartCount(token);
		if(cartSize>=0) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.CART_SIZE_FETCHED_SUCCESSFULLY, Constant.OK_RESPONSE_CODE,cartSize));
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new Response(Constant.CART_SIZE_FETCHING_FAILED, Constant.EXPECTATION_FAILED_RESPONSE_CODE));
		}
	}
}
