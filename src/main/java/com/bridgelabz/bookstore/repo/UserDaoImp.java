package com.bridgelabz.bookstore.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.UpdateDTO;
import com.bridgelabz.bookstore.utils.DateUtility;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class UserDaoImp implements UserRepo {

	@Autowired
	private SessionFactory sessionFactory;

	public void addUser(User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(user);
	}

	public User findByUserId(Long id) {
		return sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Transactional
	public List<User> getUser() {
		Session session = sessionFactory.getCurrentSession();
		Query<User> q = session.createQuery("From User");
		return q.getResultList();
	}

	public User update(UpdateDTO updateDTO, Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		user.setPassword(updateDTO.getPassword());
		user.setUpdateDateTime(DateUtility.today());
		session.update(user);
		return user;
	}

	public void delete(Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = findByUserId(id);
		session.delete(user);
	}

	@Override
	public void updatePassword(Long id, String password) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		user.setUpdateDateTime(DateUtility.today());
		user.setPassword(password);
		session.update(user);
	}

	@Override
	public void updateDateTime(Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		user.setUpdateDateTime(DateUtility.today());
		session.update(user);
	}

	@Override
	public void verify(Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		user.setVerify(true);
		session.update(user);
	}

	@Override
	public User getusersByemail(String emailId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<User> query = currentSession.createQuery("FROM User where email=:emailId");
		query.setParameter("emailId", emailId);
		return query.uniqueResult();
	}

	@Override
	public Role findByRoleId(Long roleId) {
		return sessionFactory.getCurrentSession().get(Role.class, roleId);
	}

	@Override
	public void saveRoles(Role role) {
		sessionFactory.getCurrentSession().save(role);
	}

	@Override
	public List<User> findByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM User E WHERE E.email = :email";
		Query<User> query = session.createQuery(hql);
		query.setParameter("email", email);
		return query.list();
	}

	public void updateUserStatus(Boolean userStatus, Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		user.setUpdateDateTime(DateUtility.today());
		user.setUserStatus(userStatus);
		session.update(user);
	}

	@Override
	public void saveImageUrl(String imageUrl, Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		user.setUpdateDateTime(DateUtility.today());
		user.setImageUrl(imageUrl);
		session.update(user);
	}

	public void userMerge(User user) {
		sessionFactory.getCurrentSession().merge(user);
	}

	@Override
	public User findByUserIdAndRoleId(Long userId, Long roleId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select {u.*} from user u where u.user_id=(select user_id from User_Role where user_id=:userId and role_id=:roleId)";
		Query<User> query = session.createSQLQuery(hql).addEntity("u", User.class);
		query.setParameter("userId", userId);
		query.setParameter("roleId", roleId);
		return query.uniqueResult();
	}

	@Override
	public User getusersByLoginId(String loginId) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> q = session.createQuery(
				"from User where email=:loginId or username=:loginId or mobile_number=:loginId");
		q.setParameter("loginId", loginId);
		return (User) q.uniqueResult();
	}

	@Override
	public void updateFullName(Long id, String fullName) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		user.setUpdateDateTime(DateUtility.today());
		user.setName(fullName);
		session.update(user);
	}

	public Optional<User> getUserById(long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query<User> q = session.createQuery("From User where user_id=:id");
		q.setParameter("id", userId);
		return q.uniqueResultOptional();
	}

}