package com.bridgelabz.bookstore.service;

import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Address;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.AddressDTO;
import com.bridgelabz.bookstore.repo.AddressRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private TokenUtility tokenUtility;

	@Override
	@Transactional
	public Address addAddress(AddressDTO address, String token) {
		User user = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		System.out.println(address);
		Address add = new Address();
		BeanUtils.copyProperties(address, add);
		add.setCreatedDateTime(DateUtility.today());
		add.setUser(user);
		user.getAddress().add(add);
		addressRepo.save(add);
		return add;
	}

	@Override
	@Transactional
	public Address getAddressByType(String addressType, String token) {
		User user = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		return (Address) addressRepo.findAddressByType(addressType, user.getId());
	}

}
