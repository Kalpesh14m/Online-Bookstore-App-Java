package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.model.Address;
import com.bridgelabz.bookstore.model.dto.AddressDTO;

public interface AddressService {

	Address addAddress(AddressDTO address, String token);

	Address getAddressByType(String addressType, String token);

}
