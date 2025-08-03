# Employee Leave Tracker System 🚀 

Welcome to the Employee Leave Tracker System! This is a Java-based console application designed to manage employee leave requests efficiently. Built with Hibernate for database operations and MySQL for data storage, it supports roles for Employees, Managers, and Admins, with robust input validation and Gmail-only email support. 🌟

## 📖 Overview

The Employee Leave Tracker System allows:

- Employees to apply for leaves (Casual, Sick, Earned), view their leave history, and check leave balances.
- Managers to view and approve/reject pending leave requests.
- Admins to credit leave balances and view all users.

The system enforces Gmail addresses with a single `@` symbol for user registration and login, ensuring secure and consistent email validation. 💻

## ✨ Features

### Role-Based Access:

- 🧑‍💼 **Employee:** Apply for leaves, view leave history, and check balances.
- 👨‍💼 **Manager:** View pending requests and approve/reject them with comments.
- 🛠️ **Admin:** Credit leave balances and view all registered users.

- **Leave Types:** Supports Casual, Sick, and Earned leaves with balance tracking. 📅  
- **Input Validation:** Robust handling of invalid inputs (e.g., non-numeric values, invalid emails). ✅  
- **Email Restriction:** Only Gmail addresses with exactly one `@` are allowed (e.g., `user@gmail.com`). 📧  
- **Database Integration:** Uses Hibernate with MySQL for persistent storage. 🗄️  
- **Overlap Prevention:** Ensures leave requests don’t overlap with existing approved/pending leaves. 🚫

## 🛠️ Setup Instructions

### Prerequisites

- Java Development Kit (JDK) 8 or higher ☕  
- MySQL Server 🗄️  
- Maven or Gradle for dependency management 📦  
- Git for cloning the repository 📥  

### Steps

1. Clone the Repository:

    ```bash
    git clone https://github.com/your-username/employee-leave-tracker.git
    cd employee-leave-tracker
    ```

2. Set Up MySQL Database:

    ```sql
    CREATE DATABASE leave_tracker;

    ALTER TABLE leave_requests ADD leave_type ENUM('CASUAL', 'SICK', 'EARNED');
    UPDATE leave_requests SET leave_type = 'CASUAL' WHERE leave_type IS NULL;
    ```

    Update `hibernate.cfg.xml` with your database credentials:

    ```xml
    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/leave_tracker</property>
    <property name="hibernate.connection.username">your-username</property>
    <property name="hibernate.connection.password">your-password</property>
    ```

3. Install Dependencies (example for Maven):

    ```xml
    <dependencies>
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
    ```

4. Build and Run:

    ```bash
    mvn clean install
    mvn clean package
    java -jar target/employee-leave-tracker.jar
    ```

## 🚀 Usage

### Run the Application

Start the application to see the main menu:

Employee Leave Tracker System

Login

Register

Exit
Choose an option:

shell
Copy
Edit

### Register a User 📝

Select option 2 to register:

Enter name: John Doe
Enter email (must be a Gmail address, e.g., username@gmail.com): john.doe@gmail.com
Enter password: password123
Enter role (EMPLOYEE/MANAGER/ADMIN): EMPLOYEE
Registration successful!

yaml
Copy
Edit

### Login 🔐

Select option 1 and enter your Gmail and password.

---

(Continue with Admin, Manager, Employee operations similarly using code blocks and lists.)

---

## 🐞 Error Handling

- Invalid Email: Only Gmail addresses with a single `@` are accepted.
- Invalid Inputs: Non-numeric inputs handled gracefully.
- Leave Overlaps: Prevents overlapping leave requests.
- Insufficient Balance: Rejects leave requests if balance is insufficient.

## 🤝 Contributing

Contributions are welcome! 🙌

1. Fork the repo 🍴  
2. Create a feature branch (`git checkout -b feature/your-feature`)  
3. Commit your changes (`git commit -m 'Add your feature'`)  
4. Push to the branch (`git push origin feature/your-feature`)  
5. Open a Pull Request 📬

Please follow the existing style and add tests where applicable.

## 📜 License

This project is licensed under the MIT License. See the LICENSE file for details.

## 📬 Contact

For questions or suggestions, open an issue on GitHub or contact me at [bdash3339@gmail.com](mailto:bdash3339@gmail.com). 📧

---
🎉 Happy leave tracking! 🎉
