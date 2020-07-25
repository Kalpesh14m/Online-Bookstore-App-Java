package com.bridgelabz.bookstore.repo;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.CartBooks;

@Repository
public class CartRepoImpl implements CartRepo {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public boolean saveToCart(Cart cart) {
		sessionFactory.getCurrentSession().saveOrUpdate(cart);
		return true;
	}

	@Override
	@Transactional
	public boolean saveToCartBooks(CartBooks cartBooks) {
		sessionFactory.getCurrentSession().saveOrUpdate(cartBooks);
		return true;
	}

	@Override
	public Optional<Cart> findByUserId(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Query<Cart> query = session.createQuery("From Cart where user_id=:userId");
		query.setParameter("userId", id);
		return query.uniqueResultOptional();
	}

	@Override
	@Transactional
	public boolean removeByCartBookId(Long cartBookId) {
		Session session = sessionFactory.getCurrentSession();
		Query<CartBooks> query = session.createQuery(" DELETE From CartBooks where cart_book_id=:cartBookId");
		query.setParameter("cartBookId", cartBookId);
		return (query.executeUpdate() > 0);
	}

	@Override
	public boolean deleteByCartId(long cartId) {
		Session session = sessionFactory.getCurrentSession();
		Query<CartBooks> query = session.createQuery(" DELETE From CartBooks where cart_id=:cartId");
		query.setParameter("cartId", cartId);
		return (query.executeUpdate() > 0);
	}

}
