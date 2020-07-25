package com.bridgelabz.bookstore.service;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.repo.UserRepo;

class UserServiceImpTest {

	@InjectMocks
	UserServiceImp service;

	@Mock
	UserRepo repo;
	User user = new User();
//
//	@Mock
//	RoleRepositoryImp roleRepository;
	




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

	}
	public List<Book> book(){
		Book books=new Book();
		books.setBookId(1l);
		books.setApprovalSent(true);
		books.setBookName("my love");
		Book book2= new Book();
		book2.setBookId(2l);
		book2.setApprovalSent(false);
		book2.setBookName("high priority");
		List<Book> booklist=new ArrayList<> ();
		booklist.add(books);
		booklist.add(book2);
		return booklist;	
		
	}


	@Test
	final void testFindById() {
		when(repo.findByUserId(1l)).thenReturn(user);
		service.findById(1l);
		System.out.println(user.getSellerBooks().get(0).getBookName());

		 assertEquals("pallavi", user.getName());
	}

	@Test
	final void testGetUser() {
		List<User> userlist=new ArrayList<> ();
		userlist.add(user);
		when(repo.getUser()).thenReturn(userlist);
		
		service.getUser();
		assertEquals("pallavi", user.getName());
	}
	
	@Test
	void testExpectedExceptionGetUser() {
		List<User> userlist=new ArrayList<> ();
		when(repo.getUser()).thenReturn(userlist);
		if(userlist.isEmpty())
		{
	  Assertions.assertThrows(UserNotFoundException.class, () -> {
		  service.getUser();
	  });
		}
	}	
	@Test
	void RegisterUserTest() throws UserException {
		UserServiceImp Urepo= mock(UserServiceImp.class);
	    ArgumentCaptor<RegistrationDTO> argumentCaptor = ArgumentCaptor.forClass(RegistrationDTO.class); 
	    RegistrationDTO reg= (new RegistrationDTO("Pallavi Kumari","S_1tringa","pallavikumari2207@gmail.com","A_1tring","2", "9122449097"));
		Urepo.registerUser(reg);
		verify(Urepo).registerUser(argumentCaptor.capture());
		 assertEquals("Pallavi Kumari",argumentCaptor.getValue().getName());
	}	
	@Test
	void deleteUserByIdTest() {
		long doseId = 42;

		// perform the call
		service.deleteUserById(doseId);

		// verify the mocks
		verify(repo, times(1)).delete((long) ((int) doseId));
	}


}
