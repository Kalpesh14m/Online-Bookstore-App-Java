package com.bridgelabz.bookstore.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationDTO {

	@NotEmpty(message = "Enter First Name - Registration DTO")
	@Pattern(regexp = "^[a-zA-z]+([\\s][a-zA-Z]+)*$", message = "Please Enter Valid Name")
	private String name;

	@Size(min = 3)
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "UserName Should be One Special Character,Numbers and One UpperCase")
	private String userName;

	@Pattern(regexp = "[a-zA-Z0-9][a-zA-Z0-9_.]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+", message = "Please Enter Valid Email")
	private String email;

	@Size(min = 8)
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "length should be 8 must contain atleast one uppercase, lowercase, special character and number")
	private String password;

	@NotEmpty(message = "Enter Role - Registration DTO")
	private String role;

	@Pattern(regexp = "{1}[7-9][0-9]{9}$", message = "Enter Valid Mobile Number")
	private String mobileNumber;

	public RegistrationDTO() {
		super();
	}

	public RegistrationDTO(String name, String userName, String email, String password, String role,
			String mobileNumber) {
		super();
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public RegistrationDTO setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "RegistrationDTO [name=" + name + ", userName=" + userName + ", email=" + email + ", password="
				+ password + ", moblieNumber=" + mobileNumber + ", role=" + role + "]";
	}
}
