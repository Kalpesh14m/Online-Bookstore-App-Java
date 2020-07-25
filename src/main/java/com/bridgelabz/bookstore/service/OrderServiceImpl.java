package com.bridgelabz.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.CartException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.CartBooks;
import com.bridgelabz.bookstore.model.MyOrderList;
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.CartRepo;
import com.bridgelabz.bookstore.repo.OrderRepo;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepository;
	@Autowired
	private BookRepo bookRepository;
	@Autowired
	private CartRepo cartRepository;
	@Autowired
	private TokenUtility tokenUtility;

	@Override
	@Transactional
	public Order checkOut(String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		List<CartBooks> booksFromCart = Optional.ofNullable(buyer.getUserCart().getCartBooks())
				.orElseThrow(() -> new CartException("You don't have any items in cart"));
		List<Order> orders = Optional.ofNullable(buyer.getOrders()).orElse(new ArrayList<>());
		List<Book> booksToBeOrdered = new ArrayList<>();
		Order order = new Order();
		booksFromCart.forEach(cartBook -> {
			booksToBeOrdered.add(cartBook.getBook());
			Book book = bookRepository.findByBookId(cartBook.getBook().getBookId());
			book.setQuantity(cartBook.getBook().getQuantity() - cartBook.getBookQuantity());
			
		});
		order.setBooks(booksToBeOrdered);
		order.setBuyer(buyer);
		order.setPurchaseDateTime(DateUtility.today());
		orders.add(order);
		buyer.getUserCart().setTotalBooksInCart(0);
		orderRepository.addOrder(order);
		for (int i = 0; i < booksToBeOrdered.size(); i++) {
			MyOrderList items = new MyOrderList();
			items.setQunatity(booksFromCart.get(i).getBookQuantity());
			Book book = bookRepository.findByBookId(booksToBeOrdered.get(i).getBookId());
			items.setBook(book);
			items.setBookName(book.getBookName());
			items.setTotelPrice(booksFromCart.get(i).getBookQuantity() * book.getPrice());
			items.setBuyer(buyer);
			items.setVenderName(book.getSeller().getName());
			orderRepository.addOrder(items);
		}
        cartRepository.deleteByCartId(buyer.getUserCart().getCartId());
		return order;
	}

	@Override
	public List<MyOrderList> getOrders(String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		long userId = buyer.getId();
		return orderRepository.findOrderByUserId(userId);
	}

}
