package com.bridgelabz.bookstore.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.bridgelabz.bookstore.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	AdminService adminService;
	
	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
		
	}

	@Test
	final void testGetSellersForVerification() throws Exception {
		
		mockMvc.perform(get("/admin/getSellersForVerification")
				.header("token", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzNDU3ODQwLCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjI0NTc0NTc4NDB9.HMHnoMRXV-cMnoZch2lDxofHOn9jVrgO9So9-12vVQw")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isOk());
		
	}
	
	@Test
	final void testgetBooksForVerification() throws Exception {
		
		mockMvc.perform(get("/admin/getBooksForVerification/{sellerId}","2")
				.param("sellerId", "2")
				.header("token", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzNDU3ODQwLCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjI0NTc0NTc4NDB9.HMHnoMRXV-cMnoZch2lDxofHOn9jVrgO9So9-12vVQw")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	final void testbookVerification() throws Exception {
		
		mockMvc.perform(put("/admin/bookVerification/{bookId}/{sellerId}/{verify}","2","2","true")
				.header("token", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzNDU3ODQwLCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjI0NTc0NTc4NDB9.HMHnoMRXV-cMnoZch2lDxofHOn9jVrgO9So9-12vVQw")
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

}
