package com.bridgelabz.bookstore.model;


import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Entity
@Table(name="address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private long addressId;

	@Column(name = "customer_pincode",nullable=false)
	@NotEmpty(message = "pincode cannot be blank or empty")

	private String pincode;

	@Column(name = "customer_locality",nullable=false)
	@NotEmpty(message = "locality can not be empty")

	private String locality;

	@Column(name = "customer_address",nullable=false)
	@NotEmpty(message = "address can not be empty")

	private String address;
	@Column(name = "customer_city",nullable=false)
	@NotEmpty(message = "city can not be empty")

	private String city;
	@Column(name = "customer_landmark",nullable=false)
	@NotEmpty(message = "landmark can not be empty")
	private String landmark;

	@Column(name = "address_type",nullable=false)
	@NotEmpty(message = "addressType can not be empty")
    private String addressType;
	
	@Column(name = "phoneNumber",nullable=false)
	@NotEmpty(message = "phoneNumber can not be empty")
	private String phoneNumber;
	
	@Column(name = "name",nullable=false)
	@NotEmpty(message = "Name can not be empty")
	private String name;
	
	@Column(name = "created_date_time")
	@NotNull
	private LocalDateTime createdDateTime;
	
	@ManyToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User user;

	

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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


	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

	public Address() {
		super();
	}

	public Address(long addressId, String pincode, String locality, String address, String city, String landmark,
 String addressType, String phoneNumber, String name, User user) {
		
		this.addressId = addressId;
		this.pincode = pincode;
		this.locality = locality;
		this.address = address;
		this.city = city;
		this.landmark = landmark;
		this.addressType = addressType;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", pincode=" + pincode + ", locality=" + locality + ", address="
				+ address + ", city=" + city + ", landmark=" + landmark + ", addressType=" + addressType
				+ ", phoneNumber=" + phoneNumber + ", name=" + name + ", user=" + user + "]";
	}


	
	

}

