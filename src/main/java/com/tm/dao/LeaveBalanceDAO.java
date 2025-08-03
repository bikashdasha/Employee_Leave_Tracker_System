package com.tm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.tm.entity.LeaveBalance;
import com.tm.entity.User;

public class LeaveBalanceDAO {
	private SessionFactory sessionFactory;

	public LeaveBalanceDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void saveLeaveBalance(LeaveBalance balance) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.saveOrUpdate(balance);
			session.getTransaction().commit();
		}
	}

	public LeaveBalance findByEmployee(User employee) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM LeaveBalance WHERE employee = :employee", LeaveBalance.class)
					.setParameter("employee", employee).uniqueResult();
		}
	}
}
