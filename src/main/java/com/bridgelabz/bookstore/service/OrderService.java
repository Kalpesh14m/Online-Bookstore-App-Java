package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.model.MyOrderList;
import com.bridgelabz.bookstore.model.Order;

public interface OrderService {

	public Order checkOut(String token);

	List<MyOrderList> getOrders(String token);

}
