package com.bridgelabz.bookstore.repo;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Book;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class BookDaoImple implements BookRepo {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Book> findAllBooks() {
//		return sessionFactory.getCurrentSession().createSQLQuery("select * from book where is_approved=\"1\" ")
//				.addEntity(Book.class).list();
		Session session = sessionFactory.getCurrentSession();
		Query<Book> nativeQuery = session.createSQLQuery("select * from book where is_approved=\"1\" ")
				.addEntity(Book.class);
		int pageSize = 8;
		int pageNo = 0;
		nativeQuery.setFirstResult(pageNo * pageSize);
		nativeQuery.setMaxResults(pageSize);
		return nativeQuery.getResultList();
	}

	@Override
	public List<Book> findBookByAuthorName(String authorName) {
		Session session = entityManager.unwrap(Session.class);
		Query<Book> q = session.createQuery("From Book where author_name=:name");
		q.setParameter("name", authorName);
		return q.getResultList();
	}

	@Override
	public List<Book> findBookByTitle(String bookName) {
		Session session = entityManager.unwrap(Session.class);
		Query<Book> q = session.createQuery("From Book where book_name=:name");
		q.setParameter("name", bookName);
		return q.getResultList();
	}

	@Override
	public List<Book> getBooksForVerification() {
		Session session = entityManager.unwrap(Session.class);
		Query<Book> q = session.createQuery("From Book where is_approved=:value");
		q.setParameter("value", false);
		return q.getResultList();
	}

	@Override
	public Optional<Book> getBookById(Long bookId) {
		Session session = entityManager.unwrap(Session.class);
		Query<Book> q = session.createQuery("From Book where book_id=:value");
		q.setParameter("value", bookId);
		return q.uniqueResultOptional();

	}

	@Override
	public void deleteBook(Book book) {
		Session session = sessionFactory.getCurrentSession();
		Query<Book> query = session.createQuery("DELETE FROM Book where book_id=:bookId");
		query.setParameter("bookId", book.getBookId());
//		session.remove(book);
		query.executeUpdate();
	}

	@Override
	public void save(Book book) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(book);

	}

	@Override
	public List<Book> sortBookAsc() {
		return sessionFactory.getCurrentSession().createSQLQuery("select * from book  where is_approved=\"1\"  ORDER BY price ASC ;")
				.addEntity(Book.class).list();
	}

	@Override
	public List<Book> sortBookDesc() {
		return sessionFactory.getCurrentSession().createSQLQuery("select * from book  where is_approved=\"1\" ORDER BY price DESC;")
				.addEntity(Book.class).list();
	}

	@Override
	public List<Book> findBySellerId(Long id,Integer pageNo) {
		Session session = sessionFactory.getCurrentSession();
		Query<Book> query = session.createQuery("From Book where seller_id=:id ORDER BY book_id DESC");
		query.setParameter("id", id);
		query.setFirstResult((pageNo-1)*Constant.MAX_PAGE_SIZE);
		query.setMaxResults(Constant.MAX_PAGE_SIZE);
		return query.getResultList();
	}

	@Override
	public Book findByBookId(Long bookId) {
		Session session = entityManager.unwrap(Session.class);
		Query<Book> q = session.createQuery("From Book where book_id=:value");
		q.setParameter("value", bookId);
		return q.uniqueResult();

	}

	public List<Book> findBookCount() {
		Session session = entityManager.unwrap(Session.class);
		Query<Book> q = session.createQuery(" SELECT count( * ) as total_record FROM Book where is_approved=1");
		return q.getResultList();
	}

	
	@Override
	public List<Book> findBookByPage(Integer pageNo) {
		Session session =  sessionFactory.getCurrentSession();
		 Query<Book> nativeQuery =  session.createSQLQuery("select * from book where is_approved=\"1\" ").addEntity(Book.class);
	      int pageSize=Constant.MAX_PAGE_SIZE;
		 nativeQuery.setFirstResult(pageNo* pageSize);
		 nativeQuery.setMaxResults(pageSize);
		 return  nativeQuery.getResultList();
}

	@Override
	public long findBookCount(Long sellerId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from Book where seller_id =: sellerId");
		query.setParameter("sellerId", sellerId);
		return (long) query.uniqueResult();
	}
}
