package com.tm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.tm.entity.User;

public class UserDAO {
	private SessionFactory sessionFactory;

	public UserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void saveUser(User user) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		}
	}

	public User findUserByEmail(String email) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM User WHERE email = :email", User.class).setParameter("email", email)
					.uniqueResult();
		}
	}
}
