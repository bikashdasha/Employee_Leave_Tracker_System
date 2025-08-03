package com.tm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "leave_balances")
public class LeaveBalance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "employee_id")
	private User employee;

	private int casualLeave;
	private int sickLeave;
	private int earnedLeave;

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public int getCasualLeave() {
		return casualLeave;
	}

	public void setCasualLeave(int casualLeave) {
		this.casualLeave = casualLeave;
	}

	public int getSickLeave() {
		return sickLeave;
	}

	public void setSickLeave(int sickLeave) {
		this.sickLeave = sickLeave;
	}

	public int getEarnedLeave() {
		return earnedLeave;
	}

	public void setEarnedLeave(int earnedLeave) {
		this.earnedLeave = earnedLeave;
	}
}
