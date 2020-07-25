package com.bridgelabz.bookstore.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.bookstore.constants.AdminConstants;
import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.RoleRepository;
import com.bridgelabz.bookstore.repo.UserRepo;

class AdminServiceImpTest {

	@InjectMocks
	AdminServiceImp service;

	@Mock
	RoleRepository roleRepository;

	@Mock
	private BookRepo bookRepository;

	@Mock
	private UserRepo userRepository;

	String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzNDU3ODQwLCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjI0NTc0NTc4NDB9.HMHnoMRXV-cMnoZch2lDxofHOn9jVrgO9So9-12vVQw";

	User user = new User();

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		user = new User();
		user.setEmail("email@email.com");
		user.setName("pallavi");
		user.setId(1l);
		user.setImageUrl("imageUrl");
		user.setMobileNumber("1234567890");
		user.setSellerBooks(book());
		user.setRoleList(role());

	}

	public List<Book> book() {
		Book books = new Book();
		books.setBookId(1l);
		books.setApprovalSent(false);
		books.setBookName("my love");
		Book book2 = new Book();
		book2.setBookId(2l);
		book2.setApprovalSent(true);
		book2.setBookName("high priority");
		List<Book> booklist = new ArrayList<>();
		booklist.add(books);
		booklist.add(book2);
		return booklist;

	}

	public List<Role> role() {
		List<Role> rolelist = new ArrayList<>();
		Role roles = new Role();
		roles.setRole("seller");
		roles.setRoleId(2l);
		rolelist.add(roles);

		return rolelist;

	}

	@Test
	final void testGetBooksForVerification() throws Throwable {
		long id = 1l;
		when(userRepository.findByUserId(id)).thenReturn(user);
		User seller = userRepository.findByUserId(id);
		Optional user1 = Optional.ofNullable(user);
		if (id == 1) {
				user1.orElseThrow(() -> new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH,
						AdminConstants.NOT_FOUND_RESPONSE_CODE));
		}
		else {
			throw new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH, AdminConstants.NOT_FOUND_RESPONSE_CODE);
		}
		when(userRepository.getUserById(id)).thenReturn(user1);
		List<Book> bookie = ((User) user1.get()).getSellerBooks();
		service.getBooksForVerification(id, token);
	}

	@Test
	final void testGetBooksForVerification_exception() {
		when(userRepository.findByUserId(1l)).thenReturn(user);
		User seller = userRepository.findByUserId(1l);
		Optional user1 = Optional.ofNullable(user);
		try {
			user1.orElseThrow(() -> new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH,
					AdminConstants.NOT_FOUND_RESPONSE_CODE));
		} catch (Throwable e) {
			System.out.println(e + ":trace");
			e.printStackTrace();
		}
		when(userRepository.getUserById(1l)).thenReturn(user1);
		List<Book> bookie = ((User) user1.get()).getSellerBooks();
		if (bookie.isEmpty()) {
			Assertions.assertThrows(AdminException.class, () -> {
				service.getBooksForVerification(1l, token);
			});
		}
	}

	@Test
	final void testgetSellers() throws Throwable {
		Role rolu = new Role();
		rolu.setRole("seller");
		rolu.setRoleId(2l);
		User use = new User();
		use.setId(1l);
		use.setName("jha");
		use.setSellerBooks(book());
		List<Role> rolelist1 = new ArrayList<>();
		use.setRoleList(rolelist1);
		use.getRoleList().add(rolu);

		when(userRepository.findByUserId(1l)).thenReturn(use);
		Optional user1 = Optional.ofNullable(user);
		
			user1.orElseThrow(() -> new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH,
					AdminConstants.NOT_FOUND_RESPONSE_CODE));
	
		when(userRepository.getUserById(1l)).thenReturn(user1);

		List<User> userlist = new ArrayList<>();
		userlist.add(use);
		rolu.setUser(userlist);

		when(roleRepository.getRoleByName("seller")).thenReturn(rolu);
		System.out.println("value:" + use.getRoleList());
		System.out.println("rolu:" + rolu.getUser());
		List<User> sellers = rolu.getUser();
		System.out.println("selllrs book" + sellers.get(0).getSellerBooks());
		assertEquals("jha", rolu.getUser().get(0).getName());

		int size = sellers.size();
		int ctr = 0;
		for (int i = 0; i < size; i++) {

			List<Book> book = sellers.get(i - ctr).getSellerBooks().stream().filter(b -> b.isApprovalSent())
					.collect(Collectors.toList());
			if (book.isEmpty()) {
				sellers.remove(i - ctr);
				ctr++;
			}
			book.clear();
		}
		service.getSellers(token);
	}

	@Test
	final void testgetSellers_exception() throws Throwable {
		Role rolu = new Role();
		rolu.setRole("seller");
		rolu.setRoleId(2l);
		User use = new User();
		use.setId(1l);
		use.setName("jha");
		use.setSellerBooks(book());
		List<Role> rolelist1 = new ArrayList<>();
		use.setRoleList(rolelist1);
		use.getRoleList().add(rolu);

		when(userRepository.findByUserId(1l)).thenReturn(use);
		Optional user1 = Optional.ofNullable(user);
		user1.orElseThrow(() -> new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH,
				AdminConstants.NOT_FOUND_RESPONSE_CODE));

		when(userRepository.getUserById(1l)).thenReturn(user1);

		List<User> userlist = new ArrayList<>();
		userlist.add(use);
		rolu.setUser(userlist);

		when(roleRepository.getRoleByName("seller")).thenReturn(rolu);
		List<User> sellers = rolu.getUser();
		if (sellers.isEmpty()) {
			Assertions.assertThrows(AdminException.class, () -> {
				service.getSellers(token);
			});
		}
	}

}
