package com.bridgelabz.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.repo.RoleRepository;
import com.bridgelabz.bookstore.repo.UserRepo;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class BookStoreApplication {

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private RoleRepository roleRepository;

	protected static final String[] ROLE_TYPE = new String[Constant.COUNT];
	{
		ROLE_TYPE[0] = Constant.ROLE_AS_ADMIN;
		ROLE_TYPE[1] = Constant.ROLE_AS_SELLER;
		ROLE_TYPE[2] = Constant.ROLE_AS_BUYER;
	}

	@Bean
	public void setRole() {
		Role userRole = roleRepository.getRoleById(3);
		if (userRole == null) {
			for (int counter = 0; counter < Constant.COUNT; counter++) {
				Role role = new Role((long) 1, ROLE_TYPE[counter]);
				userRepository.saveRoles(role);
			}
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

}
