package com.bridgelabz.bookstore.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Component
public class LoginDTO {

	@NotEmpty(message = "Enter LoginId - Registration DTO")
	private String loginId;
	@Size(min = 8)
	private String password;
	@NotEmpty(message = "Enter Role - Registration DTO")
	private Long role;

	public String getloginId() {
		return loginId;
	}

	public void setloginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public LoginDTO(String loginId, String password, Long role) {
		super();
		this.loginId = loginId;
		this.password = password;
		this.role = role;
	}

	public LoginDTO() {
		super();
	}

}
