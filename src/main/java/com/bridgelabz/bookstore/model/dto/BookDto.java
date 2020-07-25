package com.bridgelabz.bookstore.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

@Component
public class BookDto {

	@NotBlank(message = "Book Name can not be empty")
	private String bookName;

	@Min(value = 1, message = "Quantity can't be less than 1")
	private int quantity;

	@Min(value = 0, message = "price can't be less than 0")
	private Double price;

	@NotBlank(message = "Author Name can not be empty")
	private String authorName;

	@NotBlank(message = "provide an image for book")
	private String imageURL;

	@NotBlank(message = "Book Description can not be empty")
	private String description;

	public String getBookName() {
	return bookName;
	}

	public void setBookName(String bookName) {
	this.bookName = bookName;
	}

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

	public String getAuthorName() {
	return this.authorName;
	}

	public void setAuthorName(String authorName) {
	this.authorName = authorName;
	}

	public String getimageURL() {
	return imageURL;
	}

	public void setimageURL(String imageURL) {
	this.imageURL = imageURL;
	}

	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}
	@Override
	public String toString() {
	return "BookDto [bookName=" + bookName + ", quantity=" + quantity + ", price=" + price + ", authorName="
	+ authorName + ", imageURL=" + imageURL + ", description=" + description + "]";
	}
	
}
