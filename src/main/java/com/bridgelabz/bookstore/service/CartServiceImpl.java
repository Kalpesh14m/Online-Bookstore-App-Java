package com.bridgelabz.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookNotFoundException;
import com.bridgelabz.bookstore.exception.BookNotFoundInCartException;
import com.bridgelabz.bookstore.exception.BookOutOfStockException;
import com.bridgelabz.bookstore.exception.CartItemsLimitException;
import com.bridgelabz.bookstore.exception.ItemAlreadyExistsInCartException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.CartBooks;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.CartDto;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.CartRepo;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private TokenUtility tokenUtility;

	@Override
	@Transactional
	public Cart addtocart(String token, long bookId) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		Book bookToAddInCart = bookRepo.getBookById(bookId)
				.orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		Cart cart = Optional.ofNullable(buyer.getUserCart()).orElse(new Cart());
		List<CartBooks> listofItemsInCart = Optional.ofNullable(cart.getCartBooks()).orElse(new ArrayList<>());
		if (cart.getTotalBooksInCart() < 5) {
			listofItemsInCart.stream().filter(cartBook -> cartBook.getBook().getBookId() == bookId).findAny()
					.ifPresent(action -> {
						throw new ItemAlreadyExistsInCartException(Constant.ITEM_ALREADY_EXISTS_EXCEPTION_MESSAGE);
					});
			if (bookToAddInCart.getQuantity() <= 0) {
				throw new BookOutOfStockException(Constant.BOOK_OUT_OF_STOCK_MESSAGE);
			}
			CartBooks bookItemToBeAddedInCart = new CartBooks();
			bookItemToBeAddedInCart.setBook(bookToAddInCart);
			bookItemToBeAddedInCart.setCart(cart);
			bookItemToBeAddedInCart.setBookQuantity(1);
			bookItemToBeAddedInCart.setTotalBookPrice(bookToAddInCart.getPrice());
			listofItemsInCart.add(bookItemToBeAddedInCart);
			cart.setUser(buyer);
			cartRepo.saveToCartBooks(bookItemToBeAddedInCart);
			cart.setTotalBooksInCart(cart.getTotalBooksInCart() + 1);
			return cart;
		}
		throw new CartItemsLimitException("Cart Items Already exceeded limit of 5");
	}

	@Override
	public Cart removeBookFromCart(String token, long cartBookId) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		CartBooks bookToBeRemoved = buyer.getUserCart().getCartBooks().stream()
				.filter(cartBooks -> cartBooks.getCartBookId() == cartBookId).findAny()
				.orElseThrow(() -> new BookNotFoundInCartException(Constant.BOOK_NOT_FOUND_IN_CART_MESSAGE));
		if (cartRepo.removeByCartBookId(cartBookId)) {
			buyer.getUserCart()
					.setTotalBooksInCart(buyer.getUserCart().getTotalBooksInCart() - bookToBeRemoved.getBookQuantity());
			cartRepo.saveToCart(buyer.getUserCart());
		}
		return buyer.getUserCart();
	}

	@Override
	public Cart displayItems(String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		return buyer.getUserCart();
	}

	@Override
	public Cart addQuantity(long cartBookId, String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		CartBooks bookToBeAddQuantity = buyer.getUserCart().getCartBooks().stream()
				.filter(cartBooks -> cartBooks.getCartBookId() == cartBookId).findAny()
				.orElseThrow(() -> new BookNotFoundInCartException(Constant.BOOK_NOT_FOUND_IN_CART_MESSAGE));
		if (buyer.getUserCart().getTotalBooksInCart() <= 4) {
			if (bookToBeAddQuantity.getBook().getQuantity() <= bookToBeAddQuantity.getBookQuantity()) {
				throw new BookOutOfStockException(Constant.BOOK_OUT_OF_STOCK_MESSAGE);
			}
			bookToBeAddQuantity.setBookQuantity(bookToBeAddQuantity.getBookQuantity() + 1);
			bookToBeAddQuantity.setTotalBookPrice(
					bookToBeAddQuantity.getBook().getPrice() * bookToBeAddQuantity.getBookQuantity());
			cartRepo.saveToCartBooks(bookToBeAddQuantity);
			buyer.getUserCart().setTotalBooksInCart(buyer.getUserCart().getTotalBooksInCart() + 1);
			cartRepo.saveToCart(buyer.getUserCart());
			return buyer.getUserCart();
		}
		throw new CartItemsLimitException(Constant.CART_ITEMS_LIMIT_EXCEEDED_MESSAGE);
	}

	@Override
	public Cart updateQuantity(long cartBookId, int quantity, String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		CartBooks bookToBeUpdateQuantity = buyer.getUserCart().getCartBooks().stream()
				.filter(cartBooks -> cartBooks.getCartBookId() == cartBookId).findAny()
				.orElseThrow(() -> new BookNotFoundInCartException(Constant.BOOK_NOT_FOUND_IN_CART_MESSAGE));
		if(quantity == 0)
			throw new CartItemsLimitException(Constant.CART_ITEM_LOW_LIMIT_MESSAGE);
		buyer.getUserCart().setTotalBooksInCart(buyer.getUserCart().getTotalBooksInCart()-bookToBeUpdateQuantity.getBookQuantity());
		if((buyer.getUserCart().getTotalBooksInCart()+quantity)<6) {
			if (bookToBeUpdateQuantity.getBook().getQuantity() < quantity) {
				throw new BookOutOfStockException(Constant.BOOK_OUT_OF_STOCK_MESSAGE);
			}
			bookToBeUpdateQuantity.setBookQuantity(quantity);
			bookToBeUpdateQuantity.setTotalBookPrice(
					bookToBeUpdateQuantity.getBook().getPrice() * bookToBeUpdateQuantity.getBookQuantity());
			cartRepo.saveToCartBooks(bookToBeUpdateQuantity);
			buyer.getUserCart().setTotalBooksInCart(buyer.getUserCart().getTotalBooksInCart() + quantity);
			cartRepo.saveToCart(buyer.getUserCart());
			return buyer.getUserCart();
		}
		throw new CartItemsLimitException(Constant.CART_ITEMS_LIMIT_EXCEEDED_MESSAGE);
	}
	
	@Override
	public Cart removeQuantity(long cartBookId, String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		CartBooks bookToBeRemoveQuantity = buyer.getUserCart().getCartBooks().stream()
				.filter(cartBooks -> cartBooks.getCartBookId() == cartBookId).findAny()
				.orElseThrow(() -> new BookNotFoundInCartException(Constant.BOOK_NOT_FOUND_IN_CART_MESSAGE));
		if (buyer.getUserCart().getTotalBooksInCart() > 0) {
			if (bookToBeRemoveQuantity.getBookQuantity() == 1) {
				throw new CartItemsLimitException(Constant.CART_ITEM_LOW_LIMIT_MESSAGE);
			}
			bookToBeRemoveQuantity.setBookQuantity(bookToBeRemoveQuantity.getBookQuantity() - 1);
			bookToBeRemoveQuantity.setTotalBookPrice(
					bookToBeRemoveQuantity.getBook().getPrice() * bookToBeRemoveQuantity.getBookQuantity());
			cartRepo.saveToCartBooks(bookToBeRemoveQuantity);
			buyer.getUserCart().setTotalBooksInCart(buyer.getUserCart().getTotalBooksInCart() - 1);
			cartRepo.saveToCart(buyer.getUserCart());
			return buyer.getUserCart();
		}
		throw new CartItemsLimitException(Constant.CART_EMPTY_MESSAGE);
	}

	@Override
	public boolean placeOrder(@Valid CartDto cartDto, String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		Cart cart = Optional.ofNullable(buyer.getUserCart()).orElse(new Cart());
		List<CartBooks> listofItemsInCart = Optional.ofNullable(cart.getCartBooks()).orElse(new ArrayList<>());

		List<CartBooks> booksToCart = cartDto.getCartBooks().stream()
				.filter(cartBook -> listofItemsInCart.stream()
						.noneMatch(book -> book.getBook().getBookId().equals(cartBook.getBook().getBookId())))
				.collect(Collectors.toList());

		booksToCart.forEach(cartBookToBeChecked -> {
			if (cart.getTotalBooksInCart() < 5) {
				CartBooks bookToBeAdded = new CartBooks();
				bookToBeAdded.setBook(bookRepo.findByBookId(cartBookToBeChecked.getBook().getBookId()));
				bookToBeAdded.setBookQuantity(1);
				bookToBeAdded.setCart(cart);
				bookToBeAdded
						.setTotalBookPrice(cartBookToBeChecked.getBook().getPrice() * bookToBeAdded.getBookQuantity());
				cartRepo.saveToCartBooks(bookToBeAdded);
				cart.setTotalBooksInCart(cart.getTotalBooksInCart() + 1);
				cart.setUser(buyer);
				cartRepo.saveToCart(cart);
			} else {
				throw new CartItemsLimitException(Constant.CART_ITEMS_LIMIT_EXCEEDED_MESSAGE);
			}
		});
		return true;
	}

	@Override
	public int getCartCount(String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		Cart cart = Optional.ofNullable(buyer.getUserCart()).orElse(new Cart());
		return cart.getTotalBooksInCart();
	}

	
	

}
