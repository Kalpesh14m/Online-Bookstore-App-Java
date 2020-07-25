package com.bridgelabz.bookstore.model.dto;

import javax.validation.constraints.Min;

public class UpdateBookDto {

	@Min(value = 1, message = "Quantity can't be less than 1")
	private int quantity;

	@Min(value = 0,message = "price can't be less than 0")
	private Double price;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
