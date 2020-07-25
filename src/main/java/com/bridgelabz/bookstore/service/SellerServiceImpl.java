package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookAlreadyExistsException;
import com.bridgelabz.bookstore.exception.BookNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.model.dto.UpdateBookDto;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.TokenUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

	@Autowired
	private TokenUtility tokenUtility;

	@Autowired
	private BookRepo bookRepository;

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Method to add a new book
	 * 
	 * @param newBook,token
	 * @return Book
	 * @throws - BookAlreadyExistsException => if book already exists with same name
	 *           and price
	 */
	@Override
	public Book addBook(BookDto newBook, String token) {
		User seller = tokenUtility.authentication(token, Constant.ROLE_AS_SELLER);
		seller.getSellerBooks().stream().filter(
				book -> book.getBookName().equals(newBook.getBookName()) && book.getPrice().equals(newBook.getPrice()))
				.findAny().ifPresent(action -> {
					throw new BookAlreadyExistsException("Book Already Exists In Your Inventory");
				});

		Book book = new Book();
		BeanUtils.copyProperties(newBook, book);
		book.setCreatedDateAndTime(DateUtility.today());
		book.setSeller(seller);
		bookRepository.save(book);
		addBookInElasticsearch(book);
		return book;
	}

	/**
	 * Method to update book price and quantity
	 * 
	 * @param updateBookInfo,bookId,token
	 * @return Book
	 * @throws - BookNotFoundException => if bookId doesn't exists
	 */
	@Override
	public Book updateBook(UpdateBookDto updatedBookInfo, long bookId, String token) {
		tokenUtility.authentication(token, Constant.ROLE_AS_SELLER);
		Book bookToBeUpdated = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		bookToBeUpdated.setQuantity(updatedBookInfo.getQuantity());
		if (!bookToBeUpdated.getPrice().equals(updatedBookInfo.getPrice())) {
			bookToBeUpdated.setPrice(updatedBookInfo.getPrice());
			bookToBeUpdated.setApproved(false);
		}
		bookToBeUpdated.setLastUpdatedDateAndTime(DateUtility.today());
		bookRepository.save(bookToBeUpdated);

		updateBookInElasticSearch(bookToBeUpdated);

		return bookToBeUpdated;
	}

	/**
	 * Method to get all books related to seller
	 * 
	 * @param token
	 * @return List<Book>
	 */
	@Override
	public List<Book> getAllBooks(String token,Integer pageNo) {
		User seller = tokenUtility.authentication(token, Constant.ROLE_AS_SELLER);
		return bookRepository.findBySellerId(seller.getId(),pageNo);
	}

	/**
	 * Method to delete book
	 * 
	 * @param bookId,token
	 * @return true
	 * @throws - BookNotFoundException => if bookId doesn't exists
	 */
	@Override
	public boolean removeBook(long bookId, String token) {
		User seller = tokenUtility.authentication(token, Constant.ROLE_AS_SELLER);
		Book bookTobeDeleted = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
//		seller.getSellerBooks().remove(bookTobeDeleted);
		bookRepository.deleteBook(bookTobeDeleted);
		deleteBookInElaticSearch(bookId);
		return true;
	}

	/**
	 * Method to search Book by either authorName or book name
	 * 
	 * @param token,input
	 * @return List<Book>
	 */
	public List<Book> searchBook(String token, String input) throws IOException {
		tokenUtility.authentication(token, Constant.ROLE_AS_SELLER);
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery("*" + input + "*")
				.analyzeWildcard(true).field("authorName", 1.0f).field("bookName", 1.0f));
		searchSourceBuilder.query(builder);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		return getSearchResult(searchResponse);

	}

	private List<Book> getSearchResult(SearchResponse response) {
		SearchHit[] searchHit = response.getHits().getHits();
		List<Book> books = new ArrayList<>();
		if (searchHit.length > 0) {
			Arrays.stream(searchHit)
					.forEach(hit -> books.add(objectMapper.convertValue(hit.getSourceAsMap(), Book.class)));
		}
		return books;
	}

	@Override
	public boolean sentForApproval(long bookId, String token) {
		User seller = tokenUtility.authentication(token, Constant.ROLE_AS_SELLER);
		Book bookSentForApproval = seller.getSellerBooks().stream().filter(book -> book.getBookId() == bookId).findAny()
				.orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		if (!bookSentForApproval.isApproved() && !bookSentForApproval.isApprovalSent()) {
			bookSentForApproval.setApprovalSent(true);
			updateBookInElasticSearch(bookSentForApproval);
			return true;
		}
		return false;
	}

	@Override
	public long booksCount(String token) {
		User seller = tokenUtility.authentication(token, Constant.ROLE_AS_SELLER);
		return bookRepository.findBookCount(seller.getId());
	}
	
	@SuppressWarnings("unchecked")
	private void addBookInElasticsearch(Book book) {
		Map<String, Object> documentMapper = objectMapper.convertValue(book, Map.class);
		IndexRequest indexRequest = new IndexRequest(Constant.INDEX, Constant.TYPE, String.valueOf(book.getBookId()))
				.source(documentMapper);
		try {
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void updateBookInElasticSearch(Book bookToBeUpdated) {
		Map<String, Object> bookMapper = objectMapper.convertValue(bookToBeUpdated, Map.class);
		UpdateRequest updateRequest = new UpdateRequest(Constant.INDEX, Constant.TYPE,
				String.valueOf(bookToBeUpdated.getBookId())).doc(bookMapper);
		try {
			client.update(updateRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteBookInElaticSearch(long bookId) {
		DeleteRequest deleteRequest = new DeleteRequest(Constant.INDEX, Constant.TYPE, String.valueOf(bookId));
		try {
			client.delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
