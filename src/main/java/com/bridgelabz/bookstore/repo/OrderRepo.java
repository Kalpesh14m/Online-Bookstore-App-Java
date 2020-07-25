
package com.bridgelabz.bookstore.repo;

import java.util.List;

import com.bridgelabz.bookstore.model.MyOrderList;
import com.bridgelabz.bookstore.model.Order;

public interface OrderRepo {

	void addOrder(Order order);

	void addOrder(MyOrderList order);

	List<MyOrderList> findOrderByUserId(Long id);

	void addReview(Long id,int rating);

}
