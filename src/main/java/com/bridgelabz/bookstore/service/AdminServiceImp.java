package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.AdminConstants;
import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.RoleRepository;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.AdminTemplateService;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class AdminServiceImp implements AdminService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BookRepo bookRepository;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private AdminTemplateService mailTempletService;

	@Autowired
	private Environment environment;

	/**
	 * Method to get seller details to validate books
	 * 
	 * @param token
	 * @return List<User>
	 * @throws - AdminCredentialMismatch => if Admin credentials mismatch(Admin is
	 *           hard-coded) UserNotFoundException => if User is not registered
	 */

	@Override
	public List<User> getSellers(String token) {

		Long id = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		if (id == 1) {
			userRepository.getUserById(id)
					.orElseThrow(() -> new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH,
							AdminConstants.NOT_FOUND_RESPONSE_CODE));
		} else {
			throw new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH, AdminConstants.NOT_FOUND_RESPONSE_CODE);
		}

		List<User> sellers = roleRepository.getRoleByName(Constant.ROLE_AS_SELLER).getUser();

		if (sellers.isEmpty()) {
			throw new AdminException(AdminConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE,
					AdminConstants.NOT_FOUND_RESPONSE_CODE);
		}
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
		if (sellers.isEmpty()) {
			throw new AdminException(AdminConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE,
					AdminConstants.NOT_FOUND_RESPONSE_CODE);
		}
		return sellers;
	}

	/**
	 * Method to get books for verification
	 * 
	 * @param token
	 * @return List<User>
	 * @throws - AdminCredentialMismatch => if Admin credentials mismatch(Admin is
	 *           hard-coded) BookNotFoundException => if Book is not registered
	 */
	@Override
	public List<Book> getBooksForVerification(long sellerId, String token) {

		Long id = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		if (id == 1) {
			userRepository.getUserById(id)
					.orElseThrow(() -> new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH,
							AdminConstants.NOT_FOUND_RESPONSE_CODE));
		} else {
			throw new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH, Constant.NOT_FOUND_RESPONSE_CODE);
		}
		User seller = userRepository.findByUserId(sellerId);
		List<Book> books = seller.getSellerBooks();

		if (books.isEmpty()) {

			throw new AdminException(AdminConstants.BOOK_NOT_FOUND, AdminConstants.NOT_FOUND_RESPONSE_CODE);
		}

		List<Book> book = books.stream().filter(b -> b.isApprovalSent()).collect(Collectors.toList());

		if (book.isEmpty()) {
			throw new AdminException(Constant.BOOK_NOT_FOUND, AdminConstants.NOT_FOUND_RESPONSE_CODE);
		}
		return book;
	}

	/**
	 * Method to validate books
	 * 
	 * @param token
	 * @return List<User>
	 * @throws - AdminCredentialMismatch => if Admin credentials mismatch(Admin is
	 *           hard-coded) UserNotFoundException => if Book is not registered
	 */

	@Override
	public void bookVerification(Long bookId, Long sellerId, boolean verify, String token, String feedback)
			throws BookException {

		Long id = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		if (id == 1) {
			userRepository.getUserById(id)
					.orElseThrow(() -> new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH,
							AdminConstants.NOT_FOUND_RESPONSE_CODE));
		} else {
			throw new AdminException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH, AdminConstants.NOT_FOUND_RESPONSE_CODE);
		}
		Book book = bookRepository.getBookById(bookId).orElseThrow(
				() -> new AdminException(AdminConstants.BOOK_NOT_FOUND, AdminConstants.NOT_FOUND_RESPONSE_CODE));

		User seller = userRepository.findByUserId(sellerId);
		Role role = roleRepository.getRoleByName(Constant.ROLE_AS_SELLER);

		book.setApprovalSent(false);
		book.setVerifiedDateAndTime(DateUtility.today());
		if (verify) {
			book.setApproved(verify);
			bookRepository.save(book);
			registerMail(seller, role, environment.getProperty("book-approval-template-path"), book, feedback);
		} else {
			book.setApproved(verify);
			book.setRejectionCounts(book.getRejectionCounts() + 1);
			if (book.getRejectionCounts() > 2) {
				bookRepository.deleteBook(book);
				registerMail(seller, role, environment.getProperty("book-deletion-template-path"), book, feedback);
			} else {
				bookRepository.save(book);
				registerMail(seller, role, environment.getProperty("book-rejection-template-path"), book, feedback);
			}
		}

	}

	/**
	 * Method to register mail in rabbitmq for informing seller about verification
	 * status
	 * 
	 * @param rejectionDesc
	 * 
	 * @param User,         Role, Template
	 * @return void
	 * 
	 */
	public void registerMail(User user, Role role, String templet, Book book, String feedback) {
		String token = TokenUtility.verifyResponse(user.getId(), role.getRoleId());
		sendMail(user, token, templet, book, feedback);
	}

	/**
	 * Method to send mail for informing seller about verification status
	 * 
	 * @param User, Role, Template
	 * @return void
	 * 
	 */
	public void sendMail(User user, String token, String templet, Book book, String rejectionDesc) {
		try {
			mailTempletService.getTemplate(user, token, templet, book, rejectionDesc);
		} catch (IOException e) {

		}
	}

}
