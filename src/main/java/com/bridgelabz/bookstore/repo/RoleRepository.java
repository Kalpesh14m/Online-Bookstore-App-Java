package com.bridgelabz.bookstore.repo;

import com.bridgelabz.bookstore.model.Role;

public interface RoleRepository {

	public void save(Role roleEntity);
	public Role getRoleByName(String name);
	public Role getRoleById(int rid);
}
