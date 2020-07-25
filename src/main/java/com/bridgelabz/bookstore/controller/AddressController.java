package com.bridgelabz.bookstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Address;
import com.bridgelabz.bookstore.model.dto.AddressDTO;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.AddressService;

@RestController
@RequestMapping(value = { "/address" })

public class AddressController {

	@Autowired
	AddressService addressService;

	
	@PostMapping(value = "/addAddress", headers = "Accept=application/json")
	public ResponseEntity<Response> addAddress(@RequestBody AddressDTO address, @RequestHeader("token") String token) {

		Address addresss = addressService.addAddress(address, token);

		if (addresss != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.ADDRESS_DETAILS_ADDED, Constant.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.ADDRESS_DETAILS_FAIL, Constant.BAD_REQUEST_RESPONSE_CODE));

	}

	@GetMapping("/getAddressByType")
	public ResponseEntity<Response> getAddressByType(@RequestParam("addressType") String addressType,
			@RequestHeader("token") String token) {
		Address address = addressService.getAddressByType(addressType, token);

		if (address != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.ADDRESS_DETAILS_FOUND, Constant.OK_RESPONSE_CODE, address));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.ADDRESS_DETAILS_NOT_fOUND, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

}
