package com.bridgelabz.bookstore.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {
    
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	BookService bookservice;
	
	@MockBean
	RestHighLevelClient client;
	
	@MockBean
	ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	void setUp() throws Exception {
		
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

	@Test
	final void testGetAllBooks() throws Exception {
		mockMvc.perform(get("/books/getBooks")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	final void testsortBookByPriceAsc() throws Exception {
		mockMvc.perform(get("/books/getBooksByPriceAsc")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isNotFound());
		
	}
	
	@Test
	final void testsortBookByPriceDesc() throws Exception {
		mockMvc.perform(get("/books/getBooksByPriceDesc")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isNotFound());
		
	}
	

}
