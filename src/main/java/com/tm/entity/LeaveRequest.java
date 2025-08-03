package com.tm.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
public class LeaveRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private User employee;

	private LocalDate startDate;
	private LocalDate endDate;
	private String reason;

	@Enumerated(EnumType.STRING)
	private Status status;

	private LocalDate appliedOn;
	private String managerComment;

	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;

	public enum Status {
		PENDING, APPROVED, REJECTED
	}

	public enum LeaveType {
		CASUAL, SICK, EARNED
	}

	// Getters and setters
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDate getAppliedOn() {
		return appliedOn;
	}

	public void setAppliedOn(LocalDate appliedOn) {
		this.appliedOn = appliedOn;
	}

	public String getManagerComment() {
		return managerComment;
	}

	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}
}