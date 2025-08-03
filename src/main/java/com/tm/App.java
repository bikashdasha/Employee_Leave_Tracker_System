package com.tm;

import com.tm.dao.LeaveBalanceDAO;
import com.tm.dao.LeaveRequestDAO;
import com.tm.dao.UserDAO;
import com.tm.entity.LeaveBalance;
import com.tm.entity.LeaveRequest;
import com.tm.entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
	private static SessionFactory sessionFactory;
	private static UserDAO userDAO;
	private static LeaveRequestDAO leaveRequestDAO;
	private static LeaveBalanceDAO leaveBalanceDAO;
	private static Scanner scanner = new Scanner(System.in);
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$");

	public static void main(String[] args) {
		sessionFactory = new Configuration().configure().addAnnotatedClass(User.class)
				.addAnnotatedClass(LeaveRequest.class).addAnnotatedClass(LeaveBalance.class).buildSessionFactory();

		userDAO = new UserDAO(sessionFactory);
		leaveRequestDAO = new LeaveRequestDAO(sessionFactory);
		leaveBalanceDAO = new LeaveBalanceDAO(sessionFactory);

		while (true) {
			System.out.println("\nEmployee Leave Tracker System");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.print("Choose an option: ");

			int choice;
			try {
				choice = scanner.nextInt();
				scanner.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input! Please enter a number between 1 and 3.");
				scanner.nextLine();
				continue;
			}

			switch (choice) {
			case 1:
				login();
				break;
			case 2:
				register();
				break;
			case 3:
				sessionFactory.close();
				System.exit(0);
			default:
				System.out.println("Invalid option!");
			}
		}
	}

	private static boolean isValidEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			return false;
		}
		long atSymbolCount = email.chars().filter(ch -> ch == '@').count();
		if (atSymbolCount != 1) {
			return false;
		}
		return EMAIL_PATTERN.matcher(email).matches();
	}

	private static void register() {
		System.out.print("Enter name: ");
		String name = scanner.nextLine();
		String email;
		do {
			System.out.print("Enter email (must be a Gmail address, e.g., username@gmail.com): ");
			email = scanner.nextLine();
			if (!isValidEmail(email)) {
				System.out.println(
						"Invalid email! Must be a valid Gmail address with exactly one @ (e.g., username@gmail.com).");
			}
		} while (!isValidEmail(email));
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		System.out.print("Enter role (EMPLOYEE/MANAGER/ADMIN): ");
		String roleStr = scanner.nextLine().toUpperCase();

		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		try {
			user.setRole(User.Role.valueOf(roleStr));
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid role! Please enter EMPLOYEE, MANAGER, or ADMIN.");
			return;
		}

		userDAO.saveUser(user);

		if (user.getRole() == User.Role.EMPLOYEE) {
			LeaveBalance balance = new LeaveBalance();
			balance.setEmployee(user);
			balance.setCasualLeave(10);
			balance.setSickLeave(7);
			balance.setEarnedLeave(15);
			leaveBalanceDAO.saveLeaveBalance(balance);
		}

		System.out.println("Registration successful!");
	}

	private static void login() {
		String email;
		do {
			System.out.print("Enter email (must be a Gmail address, e.g., username@gmail.com): ");
			email = scanner.nextLine();
			if (!isValidEmail(email)) {
				System.out.println(
						"Invalid email! Must be a valid Gmail address with exactly one @ (e.g., username@gmail.com).");
			}
		} while (!isValidEmail(email));
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		User user = userDAO.findUserByEmail(email);
		if (user != null && user.getPassword().equals(password)) {
			switch (user.getRole()) {
			case EMPLOYEE:
				employeeMenu(user);
				break;
			case MANAGER:
				managerMenu(user);
				break;
			case ADMIN:
				adminMenu(user);
				break;
			}
		} else {
			System.out.println("Invalid credentials!");
		}
	}

	private static void employeeMenu(User user) {
		while (true) {
			System.out.println("\nEmployee Menu");
			System.out.println("1. Apply Leave");
			System.out.println("2. View Leave History");
			System.out.println("3. View Leave Balance");
			System.out.println("4. Logout");
			System.out.print("Choose an option: ");

			int choice;
			try {
				choice = scanner.nextInt();
				scanner.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input! Please enter a number between 1 and 4.");
				scanner.nextLine();
				continue;
			}

			switch (choice) {
			case 1:
				applyLeave(user);
				break;
			case 2:
				viewLeaveHistory(user);
				break;
			case 3:
				viewLeaveBalance(user);
				break;
			case 4:
				return;
			default:
				System.out.println("Invalid option!");
			}
		}
	}

	private static void applyLeave(User user) {
		System.out.print("Enter start date (yyyy-MM-dd): ");
		LocalDate startDate;
		try {
			startDate = LocalDate.parse(scanner.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid date format! Please use yyyy-MM-dd.");
			return;
		}
		System.out.print("Enter end date (yyyy-MM-dd): ");
		LocalDate endDate;
		try {
			endDate = LocalDate.parse(scanner.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid date format! Please use yyyy-MM-dd.");
			return;
		}
		System.out.print("Enter reason: ");
		String reason = scanner.nextLine();
		System.out.print("Enter leave type (CASUAL/SICK/EARNED): ");
		String leaveType = scanner.nextLine().toUpperCase();

		if (startDate.isAfter(endDate)) {
			System.out.println("Start date must be before end date!");
			return;
		}

		LeaveBalance balance = leaveBalanceDAO.findByEmployee(user);
		int days = (int) (endDate.toEpochDay() - startDate.toEpochDay() + 1);
		boolean hasEnoughBalance = false;

		LeaveRequest.LeaveType leaveTypeEnum;
		try {
			leaveTypeEnum = LeaveRequest.LeaveType.valueOf(leaveType);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid leave type! Please enter CASUAL, SICK, or EARNED.");
			return;
		}

		switch (leaveTypeEnum) {
		case CASUAL:
			hasEnoughBalance = balance.getCasualLeave() >= days;
			break;
		case SICK:
			hasEnoughBalance = balance.getSickLeave() >= days;
			break;
		case EARNED:
			hasEnoughBalance = balance.getEarnedLeave() >= days;
			break;
		}

		if (!hasEnoughBalance) {
			System.out.println("Insufficient leave balance!");
			return;
		}

		List<LeaveRequest> requests = leaveRequestDAO.findLeaveHistoryByEmployee(user);
		for (LeaveRequest request : requests) {
			if (request.getStatus() != LeaveRequest.Status.REJECTED
					&& !(endDate.isBefore(request.getStartDate()) || startDate.isAfter(request.getEndDate()))) {
				System.out.println("Leave overlaps with existing leave!");
				return;
			}
		}

		LeaveRequest request = new LeaveRequest();
		request.setEmployee(user);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		request.setReason(reason);
		request.setStatus(LeaveRequest.Status.PENDING);
		request.setAppliedOn(LocalDate.now());
		request.setLeaveType(leaveTypeEnum);

		leaveRequestDAO.saveLeaveRequest(request);
		System.out.println("Leave request submitted successfully!");
	}

	private static void viewLeaveHistory(User user) {
		List<LeaveRequest> requests = leaveRequestDAO.findLeaveHistoryByEmployee(user);
		if (requests.isEmpty()) {
			System.out.println("No leave history found.");
		} else {
			for (LeaveRequest request : requests) {
				System.out.printf("ID: %d, From: %s, To: %s, Type: %s, Reason: %s, Status: %s, Comment: %s%n",
						request.getId(), request.getStartDate(), request.getEndDate(),
						request.getLeaveType() != null ? request.getLeaveType() : "Unknown", request.getReason(),
						request.getStatus(),
						request.getManagerComment() != null ? request.getManagerComment() : "None");
			}
		}
	}

	private static void viewLeaveBalance(User user) {
		LeaveBalance balance = leaveBalanceDAO.findByEmployee(user);
		if (balance == null) {
			System.out.println("No leave balance found.");
		} else {
			System.out.printf("Casual: %d, Sick: %d, Earned: %d%n", balance.getCasualLeave(), balance.getSickLeave(),
					balance.getEarnedLeave());
		}
	}

	private static void managerMenu(User user) {
		while (true) {
			System.out.println("\nManager Menu");
			System.out.println("1. View Pending Requests");
			System.out.println("2. Approve/Reject Request");
			System.out.println("3. Logout");
			System.out.print("Choose an option (1-3): ");

			int choice;
			try {
				choice = scanner.nextInt();
				scanner.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input! Please enter a number between 1 and 3.");
				scanner.nextLine();
				continue;
			}

			switch (choice) {
			case 1:
				viewPendingRequests();
				break;
			case 2:
				processLeaveRequest();
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid option! Please choose 1, 2, or 3.");
			}
		}
	}

	private static void viewPendingRequests() {
		List<LeaveRequest> requests = leaveRequestDAO.findPendingRequestsForManager();
		if (requests.isEmpty()) {
			System.out.println("No pending leave requests.");
		} else {
			for (LeaveRequest request : requests) {
				System.out.printf("ID: %d, Employee: %s, From: %s, To: %s, Type: %s, Reason: %s%n", request.getId(),
						request.getEmployee().getName(), request.getStartDate(), request.getEndDate(),
						request.getLeaveType() != null ? request.getLeaveType() : "Unknown", request.getReason());
			}
		}
	}

	private static void processLeaveRequest() {
		System.out.print("Enter request ID: ");
		int requestId;
		try {
			requestId = scanner.nextInt();
			scanner.nextLine();
		} catch (java.util.InputMismatchException e) {
			System.out.println("Invalid request ID! Please enter a valid number.");
			scanner.nextLine();
			return;
		}
		System.out.print("Enter action (APPROVED/REJECTED): ");
		String action = scanner.nextLine().toUpperCase();
		try {
			LeaveRequest.Status.valueOf(action);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid action! Please enter APPROVED or REJECTED.");
			return;
		}
		System.out.print("Enter comment (optional): ");
		String comment = scanner.nextLine();

		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			LeaveRequest request = session.get(LeaveRequest.class, requestId);
			if (request != null && request.getStatus() == LeaveRequest.Status.PENDING) {
				request.setStatus(LeaveRequest.Status.valueOf(action));
				request.setManagerComment(comment);

				if (action.equals("APPROVED")) {
					LeaveBalance balance = leaveBalanceDAO.findByEmployee(request.getEmployee());
					int days = (int) (request.getEndDate().toEpochDay() - request.getStartDate().toEpochDay() + 1);
					if (request.getLeaveType() == null) {
						// Handle legacy requests by assuming CASUAL
						balance.setCasualLeave(balance.getCasualLeave() - days);
					} else {
						switch (request.getLeaveType()) {
						case CASUAL:
							balance.setCasualLeave(balance.getCasualLeave() - days);
							break;
						case SICK:
							balance.setSickLeave(balance.getSickLeave() - days);
							break;
						case EARNED:
							balance.setEarnedLeave(balance.getEarnedLeave() - days);
							break;
						}
					}
					leaveBalanceDAO.saveLeaveBalance(balance);
				}

				session.update(request);
				session.getTransaction().commit();
				System.out.println("Request processed successfully!");
			} else {
				System.out.println("Invalid request ID or request already processed!");
			}
		}
	}

	private static void adminMenu(User user) {
		while (true) {
			System.out.println("\nAdmin Menu");
			System.out.println("1. Credit Leave Balance");
			System.out.println("2. View All Users");
			System.out.println("3. Logout");
			System.out.print("Choose an option (1-3): ");

			int choice;
			try {
				choice = scanner.nextInt();
				scanner.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input! Please enter a number between 1 and 3.");
				scanner.nextLine();
				continue;
			}

			switch (choice) {
			case 1:
				creditLeaveBalance();
				break;
			case 2:
				viewAllUsers();
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid option! Please choose 1, 2, or 3.");
			}
		}
	}

	private static void creditLeaveBalance() {
		System.out.print("Enter employee email (must be a Gmail address, e.g., username@gmail.com): ");
		String email = scanner.nextLine();
		if (!isValidEmail(email)) {
			System.out.println(
					"Invalid email! Must be a valid Gmail address with exactly one @ (e.g., username@gmail.com).");
			return;
		}
		System.out.print("Enter casual leave to credit: ");
		int casual;
		try {
			casual = scanner.nextInt();
		} catch (java.util.InputMismatchException e) {
			System.out.println("Invalid input! Please enter a valid number for casual leave.");
			scanner.nextLine();
			return;
		}
		System.out.print("Enter sick leave to credit: ");
		int sick;
		try {
			sick = scanner.nextInt();
		} catch (java.util.InputMismatchException e) {
			System.out.println("Invalid input! Please enter a valid number for sick leave.");
			scanner.nextLine();
			return;
		}
		System.out.print("Enter earned leave to credit: ");
		int earned;
		try {
			earned = scanner.nextInt();
		} catch (java.util.InputMismatchException e) {
			System.out.println("Invalid input! Please enter a valid number for earned leave.");
			scanner.nextLine();
			return;
		}
		scanner.nextLine();

		User employee = userDAO.findUserByEmail(email);
		if (employee != null) {
			LeaveBalance balance = leaveBalanceDAO.findByEmployee(employee);
			balance.setCasualLeave(balance.getCasualLeave() + casual);
			balance.setSickLeave(balance.getSickLeave() + sick);
			balance.setEarnedLeave(balance.getEarnedLeave() + earned);
			leaveBalanceDAO.saveLeaveBalance(balance);
			System.out.println("Leave balance updated successfully!");
		} else {
			System.out.println("Employee not found!");
		}
	}

	private static void viewAllUsers() {
		try (Session session = sessionFactory.openSession()) {
			List<User> users = session.createQuery("FROM User", User.class).list();
			if (users.isEmpty()) {
				System.out.println("No users found.");
			} else {
				for (User u : users) {
					System.out.printf("ID: %d, Name: %s, Email: %s, Role: %s%n", u.getId(), u.getName(), u.getEmail(),
							u.getRole());
				}
			}
		}
	}
}