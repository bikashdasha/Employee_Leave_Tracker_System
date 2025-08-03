package com.tm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.tm.entity.LeaveRequest;
import com.tm.entity.User;

public class LeaveRequestDAO {
	private SessionFactory sessionFactory;

	public LeaveRequestDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void saveLeaveRequest(LeaveRequest request) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.save(request);
			session.getTransaction().commit();
		}
	}

	public List<LeaveRequest> findPendingRequestsForManager() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM LeaveRequest WHERE status = :status", LeaveRequest.class)
					.setParameter("status", LeaveRequest.Status.PENDING).list();
		}
	}

	public List<LeaveRequest> findLeaveHistoryByEmployee(User employee) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM LeaveRequest WHERE employee = :employee", LeaveRequest.class)
					.setParameter("employee", employee).list();
		}
	}
}
