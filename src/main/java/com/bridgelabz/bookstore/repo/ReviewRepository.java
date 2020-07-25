package com.bridgelabz.bookstore.repo;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.Review;

@Repository
public class ReviewRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void update(Review review) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.update(review);
	}
}
