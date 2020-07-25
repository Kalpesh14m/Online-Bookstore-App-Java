package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.Constant;
//import com.bridgelabz.bookstore.model.MyOrder;
//import com.bridgelabz.bookstore.model.MyOrderItems;
import com.bridgelabz.bookstore.model.MyOrderList;
//import com.bridgelabz.bookstore.model.MyOrder;
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.OrderService;


@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping(value = "/checkOut")
	public ResponseEntity<Response> checkOut(@RequestHeader("token") String token) {
		Order order = orderService.checkOut(token);
		if (order != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.ORDER_PLACED_SUCCESSFULLY, Constant.OK_RESPONSE_CODE, order));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.ORDER_PLACED_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
	}
	
	@GetMapping(value = "/myorders")
	public ResponseEntity<Response> myOrder(@RequestHeader("token") String token) {
		List<MyOrderList> myorders = orderService.getOrders(token);
		if (myorders != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.LIST_OF_ORDERS, Constant.OK_RESPONSE_CODE, myorders));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.ORDER_EMPTY, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

}
