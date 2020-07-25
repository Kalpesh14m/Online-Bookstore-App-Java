package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
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

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class BookServiceImp implements BookService {

	@Autowired
	private BookRepo bookRepository;

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepo userRepository;

	public List<Book> findBookByAuthorNameAndTile(String text) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("bookentity");
		searchRequest.types("doc");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text).lenient(true).field("authorName").field("bookName"))
				.should(QueryBuilders.queryStringQuery("*" + text + "*").lenient(true).field("authorName")
						.field("bookName"));

		searchSourceBuilder.query(query);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getSearchResult(searchResponse);
	}

	@Override
	public List<Book> findAllBook() {

		return bookRepository.findAllBooks();
	}

	@Override
	public void addBook(BookDto details, Long userId) {

		Book bookEntity = new Book();
		BeanUtils.copyProperties(details, bookEntity);
		bookEntity.setCreatedDateAndTime(LocalDateTime.now());
		bookEntity.setLastUpdatedDateAndTime(LocalDateTime.now());
		bookEntity.setVerifiedDateAndTime(LocalDateTime.now());
		bookEntity.setApproved(false);
		bookEntity.setRejectionCounts(0);

		User user = userRepository.findByUserId(userId);

		user.getSellerBooks().add(bookEntity);
		userRepository.addUser(user);
	}

	@Override
	public List<Book> sortBookByAsc() {
		return bookRepository.sortBookAsc();
	}

	@Override
	public List<Book> sortBookByDesc() {
		return bookRepository.sortBookDesc();
	}

	private List<Book> getSearchResult(SearchResponse response) {

		SearchHit[] searchHit = response.getHits().getHits();
		List<Book> u = new ArrayList<>();
		for (SearchHit hit : searchHit) {
			u.add(objectMapper.convertValue(hit.getSourceAsMap(), Book.class));
		}
		return u;
	}

	@Override
	public List<Book> findBookCount() {
		return bookRepository.findBookCount();
	}

	@Override
	public List<Book> findBookByPage(Integer pageNo) {
		return bookRepository.findBookByPage(pageNo);
	}
}
