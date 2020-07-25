package com.bridgelabz.bookstore.model.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

@Component
public class AddressDTO {
	private String address;
	private String locality;
	private String name;
	private String city;
	private String landmark;
	private String pincode;
	
	@NotEmpty(message = "addressType can not be empty")
	private String addressType;
	
	
	private String phoneNumber;
	 
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	public String getaddressType() {
		return addressType;
	}
	public void setaddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "AddressDTO [address=" + address + ", locality=" + locality + ", name=" + name + ", city=" + city
				+ ", landmark=" + landmark + ", pincode=" + pincode + ", addressType=" + addressType + ", phoneNumber="
				+ phoneNumber + "]";
	}
	
	
}

