package com.bridgelabz.bookstore.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.MyOrderList;
import com.bridgelabz.bookstore.model.Order;

@Repository
public class OrderDaoImpl implements OrderRepo {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void addOrder(Order order) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(order);
	}
	
	@Override
	@Transactional
	public void addOrder(MyOrderList order) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(order);
	}
	
	@Override
	@Transactional
	public List<MyOrderList> findOrderByUserId(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Query<MyOrderList> query = session.createQuery("From MyOrderList where user_id=:id");
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public void addReview(Long id,int rating) 
	{
		Session session = sessionFactory.getCurrentSession();
		MyOrderList myOrder = session.get(MyOrderList.class, id);
		myOrder.setReview(rating);
		session.update(myOrder);
	}

}
