package com.bridgelabz.bookstore.model.dto;

import org.springframework.stereotype.Component;

@Component
public class RoleDTO {

	private String role;

	public RoleDTO() {
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
