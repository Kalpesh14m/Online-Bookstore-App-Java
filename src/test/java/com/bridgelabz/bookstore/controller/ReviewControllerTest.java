package com.bridgelabz.bookstore.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.bridgelabz.bookstore.model.dto.LoginDTO;
import com.bridgelabz.bookstore.model.dto.ReviewDTO;
import com.bridgelabz.bookstore.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	ReviewService reviewService;
	
	

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

	@Test
	final void testAddReview() throws Exception {
		mockMvc.perform(post("/review/{bookId}","2")
				.param("bookId", "2")
				.header("token", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzNDU3ODQwLCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjI0NTc0NTc4NDB9.HMHnoMRXV-cMnoZch2lDxofHOn9jVrgO9So9-12vVQw")
				.content(asJsonString(new ReviewDTO(4,"VERY good")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isOk());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	@Test
	final void testGetReview() throws Exception {
		mockMvc.perform(get("/review/{bookId}","2")
				.param("bookId", "2")
				.header("token", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzNDU3ODQwLCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjI0NTc0NTc4NDB9.HMHnoMRXV-cMnoZch2lDxofHOn9jVrgO9So9-12vVQw")
				.content(asJsonString(new ReviewDTO(4,"VERY good")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isOk());
	
	}

}
