package com.bridgelabz.bookstore.repo;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.model.Wishlist;

@Repository

public class WishlishRepoImpl implements WishlistRepo {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public boolean saveToWishlist(Wishlist wishlist) {
		sessionFactory.getCurrentSession().saveOrUpdate(wishlist);
		return true;
	}
	
	public Optional<Wishlist> findByUserId(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Query<Wishlist> query = session.createQuery("From Wishlist where user_id=:userId");
		query.setParameter("userId", id);
		return query.uniqueResultOptional();
	}

	

}
