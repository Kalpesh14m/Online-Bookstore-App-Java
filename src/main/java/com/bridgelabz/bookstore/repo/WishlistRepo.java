package com.bridgelabz.bookstore.repo;

import java.util.Optional;

import com.bridgelabz.bookstore.model.Wishlist;

public interface WishlistRepo {
boolean saveToWishlist(Wishlist wishlist);
	

	Optional<Wishlist> findByUserId(Long id);

}
