package com.bridgelabz.bookstore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.bridgelabz.bookstore.model.dto.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id", nullable = false)
	private Long roleId;

	@Column(name = "role_name", nullable = false)
	private String role;

	public Role() {
	}

	public Role(RoleDTO req) {
		this.role = req.getRole();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole(String role) {
		return role;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@JsonIgnore
	@ManyToMany(mappedBy = "roleList")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> users;

	public List<User> getUser() {
		return users;
	}

	public void setUser(List<User> users) {
		this.users = users;
	}

	public Role(Long roleId, String role) {
		super();
		this.roleId = roleId;
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId +", role=" + role + "]";
	}

}
