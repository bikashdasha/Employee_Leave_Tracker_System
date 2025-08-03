Employee Leave Tracker System 🚀
Welcome to the Employee Leave Tracker System! This is a Java-based console application designed to manage employee leave requests efficiently. Built with Hibernate for database operations and MySQL for data storage, it supports roles for Employees, Managers, and Admins, with robust input validation and Gmail-only email support. 🌟
📖 Overview
The Employee Leave Tracker System allows:

Employees to apply for leaves (Casual, Sick, Earned), view their leave history, and check leave balances.
Managers to view and approve/reject pending leave requests.
Admins to credit leave balances and view all users.

The system enforces Gmail addresses with a single @ symbol for user registration and login, ensuring secure and consistent email validation. 💻
✨ Features

Role-Based Access:

🧑‍💼 Employee: Apply for leaves, view leave history, and check balances.
👨‍💼 Manager: View pending requests and approve/reject them with comments.
🛠️ Admin: Credit leave balances and view all registered users.


Leave Types: Supports Casual, Sick, and Earned leaves with balance tracking. 📅
Input Validation: Robust handling of invalid inputs (e.g., non-numeric values, invalid emails). ✅
Email Restriction: Only Gmail addresses with exactly one @ are allowed (e.g., user@gmail.com). 📧
Database Integration: Uses Hibernate with MySQL for persistent storage. 🗄️
Overlap Prevention: Ensures leave requests don’t overlap with existing approved/pending leaves. 🚫

🛠️ Setup Instructions
Follow these steps to set up and run the application locally:
Prerequisites

Java Development Kit (JDK) 8 or higher ☕
MySQL Server 🗄️
Maven or Gradle for dependency management 📦
Git for cloning the repository 📥

Steps

Clone the Repository:
bashgit clone https://github.com/your-username/employee-leave-tracker.git
cd employee-leave-tracker

Set Up MySQL Database:

Create a MySQL database (e.g., leave_tracker).
Update hibernate.cfg.xml with your database credentials:
xml<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/leave_tracker</property>
<property name="hibernate.connection.username">your-username</property>
<property name="hibernate.connection.password">your-password</property>

Run the following SQL to create the leave_type column:
sqlALTER TABLE leave_requests ADD leave_type ENUM('CASUAL', 'SICK', 'EARNED');
UPDATE leave_requests SET leave_type = 'CASUAL' WHERE leave_type IS NULL;



Install Dependencies:

Ensure you have the following in your pom.xml (for Maven):
xml<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.6.15.Final</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>

Run mvn clean install to download dependencies.


Build and Run:
bashmvn clean package
java -jar target/employee-leave-tracker.jar


🚀 Usage

Run the Application:

Start the application to see the main menu:
textEmployee Leave Tracker System
1. Login
2. Register
3. Exit
Choose an option:



Register a User 📝:

Select option 2 to register.
Enter a name, Gmail address (e.g., user@gmail.com), password, and role (EMPLOYEE, MANAGER, or ADMIN).
Example:
textEnter name: John Doe
Enter email (must be a Gmail address, e.g., username@gmail.com): john.doe@gmail.com
Enter password: password123
Enter role (EMPLOYEE/MANAGER/ADMIN): EMPLOYEE
Registration successful!



Login 🔐:

Select option 1 and enter your Gmail address and password.
Based on your role, you’ll see the respective menu (Employee, Manager, or Admin).


Admin Operations 🛠️:

Credit Leave Balance:
textAdmin Menu
1. Credit Leave Balance
2. View All Users
3. Logout
Choose an option (1-3): 1
Enter employee email (must be a Gmail address, e.g., username@gmail.com): john.doe@gmail.com
Enter casual leave to credit: 5
Enter sick leave to credit: 3
Enter earned leave to credit: 2
Leave balance updated successfully!

View All Users:
textChoose an option (1-3): 2
ID: 1, Name: John, Email: john.doe@gmail.com, Role: EMPLOYEE
ID: 2, Name: Manager, Email: m@gmail.com, Role: MANAGER
ID: 3, Name: Admin, Email: admin@gmail.com, Role: ADMIN



Manager Operations 👨‍💼:

View Pending Requests:
textManager Menu
1. View Pending Requests
2. Approve/Reject Request
3. Logout
Choose an option (1-3): 1
ID: 1, Employee: John, From: 2025-08-10, To: 2025-08-13, Type: CASUAL, Reason: helth

Approve/Reject Request:
textChoose an option (1-3): 2
Enter request ID: 1
Enter action (APPROVED/REJECTED): APPROVED
Enter comment (optional): OK
Request processed successfully!



Employee Operations 🧑‍💼:

Apply for a leave, view leave history, or check leave balance (see code for details).



🐞 Error Handling

Invalid Email: Only Gmail addresses with a single @ are accepted (e.g., john@@gmail.com is rejected).
Invalid Inputs: Non-numeric inputs for menu options or leave credits are handled gracefully.
Leave Overlaps: Prevents employees from applying for overlapping leaves.
Insufficient Balance: Rejects leave requests if the balance is insufficient.

🤝 Contributing
Contributions are welcome! 🙌 To contribute:

Fork the repository 🍴.
Create a feature branch (git checkout -b feature/your-feature).
Commit your changes (git commit -m 'Add your feature').
Push to the branch (git push origin feature/your-feature).
Open a Pull Request 📬.

Please ensure your code follows the existing style and includes tests where applicable.
📜 License
This project is licensed under the MIT License. See the LICENSE file for details.
📬 Contact
For questions or suggestions, open an issue on GitHub or contact me at [bdash3339@gmail.com]. 📧
Happy leave tracking! 🎉
