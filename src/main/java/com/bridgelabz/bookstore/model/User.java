
package com.bridgelabz.bookstore.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "full_name", nullable = false)
	@Size(min = 3)
	private String name;

	@Column(name = "username", unique = true, nullable = false)
	private String userName;

	@Column(name = "email", unique = true, nullable = false)
	@Email
	private String email;

	@Column(name = "password", nullable = false)
	@Size(min = 3)
	private String password;

	@Column(name = "mobile_number", unique = true, length = 10)
	@NotNull
	private String mobileNumber;

	@Column(name = "is_verify", columnDefinition = "boolean default false")
	@NotNull
	private boolean isVerify;

	@Column(name = "registration_date_time")
	@NotNull
	private LocalDateTime registrationDateTime;

	@Column(name = "update_date_time")
	@NotNull
	private LocalDateTime updateDateTime;

	@Column(name = "user_status", columnDefinition = "boolean default false")
	@NotNull
	private boolean userStatus;

	@Column(name = "imageUrl")
	private String imageUrl;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "User_Role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	public List<Role> roleList;

	@JsonIgnore
	@OneToMany(mappedBy = "seller")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Book> sellerBooks;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, optional = true, mappedBy = "user")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Cart userCart;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "wishlist_id")
	private Wishlist userWishlist;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Order> orders;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MyOrderList> myOrderList;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "user_id")
	private List<Review> review;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "user_id")
	private List<ReviewApp> reviewApp;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Address> address;

	public List<MyOrderList> getMyOrderList() {
		return myOrderList;
	}

	public void setMyOrderList(List<MyOrderList> myOrderList) {
		this.myOrderList = myOrderList;
	}

	public Wishlist getUserWishlist() {
		return userWishlist;
	}

	public void setUserWishlist(Wishlist userWishlist) {
		this.userWishlist = userWishlist;
	}

	public Cart getUserCart() {
		return userCart;
	}

	public void setUserCart(Cart userCart) {
		this.userCart = userCart;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public List<ReviewApp> getReviewApp() {
		return reviewApp;
	}

	public void setReviewApp(List<ReviewApp> reviewApp) {
		this.reviewApp = reviewApp;
	}

	public User(String fullName, String userName, String email, String password, String mobileNumber) {
		super();
		this.name = fullName;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.mobileNumber = mobileNumber;
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public boolean isUserStatus() {
		return userStatus;
	}

	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Book> getSellerBooks() {
		return sellerBooks;
	}

	public void setSellerBooks(List<Book> sellerBooks) {
		this.sellerBooks = sellerBooks;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", userName=" + userName + ", email=" + email + ", password="
				+ password + ", mobileNumber=" + mobileNumber + ", isVerify=" + isVerify + ", registrationDateTime="
				+ registrationDateTime + ", updateDateTime=" + updateDateTime + ", userStatus=" + userStatus
				+ ", imageUrl=" + imageUrl + ", roleList=" + roleList + ", sellerBooks=" + sellerBooks + ", userCart="
				+ userCart + ", userWishlist=" + userWishlist + ", review=" + review + "]";
	}


	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}


}
