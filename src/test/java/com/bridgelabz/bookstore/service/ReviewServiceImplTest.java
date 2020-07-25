package com.bridgelabz.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import com.bridgelabz.bookstore.constants.AdminConstants;
import com.bridgelabz.bookstore.constants.ReviewConstants;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Review;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.ReviewDTO;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.OrderRepo;
import com.bridgelabz.bookstore.repo.UserRepo;

class ReviewServiceImplTest {
	@InjectMocks
	ReviewServiceImpl service;
	
	@Mock
	UserRepo userRepository;
	
	@Mock
	BookRepo bookRepository;
	@Mock
	OrderRepo orderRepository;
	
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
		List<Review> Rlist=new ArrayList<> ();
		user.setReview(Rlist);
	}
public List<Review> review(){
	Review reviews=new Review();
	reviews.setRating(5);
	reviews.setReviewId(1l);
	reviews.setReview("acceptable");
	List<Review> Reviewlist=new ArrayList<> ();
	Reviewlist.add(reviews);
	return Reviewlist;
}
	public List<Book> book(){
		Book books=new Book();
		books.setBookId(1l);
		books.setApprovalSent(false);
		books.setBookName("my love");
		Book book2= new Book();
		book2.setBookId(2l);
		book2.setApprovalSent(true);
		book2.setBookName("high priority");
		List<Book> booklist=new ArrayList<> ();
		booklist.add(books);
		booklist.add(book2);
		return booklist;		
	}
	public List<Role> role(){
		List<Role> rolelist=new ArrayList<> ();
		Role roles=new Role();
		roles.setRole("seller");
		roles.setRoleId(2l);
		rolelist.add(roles);
		return rolelist;
	}
	@Test
	final void testAddRating()  {
		Optional user1 = Optional.ofNullable(user);
			try {
				user1.orElseThrow(() -> new UserNotFoundException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH,
						AdminConstants.NOT_FOUND_RESPONSE_CODE));
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("ex:"+ e1);
			}
		when(userRepository.getUserById(1l)).thenReturn(user1);	
		Book books=new Book();
		books.setBookId(1l);
		books.setApprovalSent(false);
		books.setBookName("my love");
		//books.setReview(review());
		List<Review> Rlist=new ArrayList<> ();
		books.setReview(Rlist);
		
		Optional book1= Optional.ofNullable(books);
		try {
			book1.orElseThrow(() -> new UserNotFoundException(ReviewConstants.BOOK_NOT_FOUND,
					ReviewConstants.NOT_FOUND_RESPONSE_CODE));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("book e"+ e);
		}
		
		when(bookRepository.getBookById(1l)).thenReturn(book1);	
		System.out.println("review is:" + user.getReview());
		Review review = new Review();
		ReviewDTO DTO= new ReviewDTO(1,"very good");
		BeanUtils.copyProperties(DTO, review);
		User use=(User) user1.get();
		use.getReview().add(review);
		System.out.println("naya wala"+use.getReview());
		
		userRepository.addUser(use);
		Book bookie=(Book) book1.get();
		bookie.getReview().add(review);
		System.out.println("bookid:"+ books.getBookId());
		bookRepository.save(books);
		System.out.println("book review:"+ bookie.getReview());
		//orderRepository.addReview(books.getBookId(), review.getRating());
		service.addRating(token, books.getBookId(), DTO, 1l);
		assertEquals("very good",review.getReview());
	}

//	@Test
//	final void testGetReview() {
//		fail("Not yet implemented");
//	}

}
