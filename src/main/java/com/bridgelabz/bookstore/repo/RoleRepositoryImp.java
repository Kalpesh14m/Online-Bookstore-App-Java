package com.bridgelabz.bookstore.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.model.Role;

@Repository
@Transactional
public class RoleRepositoryImp implements RoleRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void save(Role roleEntity) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(roleEntity);
	}

	@Transactional
	public Role getRoleByName(String name) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Role> q = currentSession.createQuery("From Role where role_name=:name");
		q.setParameter("name", name);

		return (Role) q.uniqueResult();

	}

	@Transactional
	public Role getRoleById(int rid) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Role> q = currentSession.createQuery("From Role where role_id=:id");
		q.setParameter("id", rid);
		return q.uniqueResult();
	}

}
