<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Employee Leave Tracker System</title>
  <style>
    body {
      font-family: 'Segoe UI', sans-serif;
      background-color: #f9f9f9;
      color: #333;
      line-height: 1.6;
      margin: 2rem;
    }

    h1, h2, h3 {
      color: #2c3e50;
    }

    h1 {
      text-align: center;
    }

    code, pre {
      background-color: #eee;
      padding: 0.5rem;
      border-radius: 5px;
      display: block;
      white-space: pre-wrap;
      margin-bottom: 1rem;
    }

    ul, ol {
      margin-bottom: 1rem;
    }

    .badge {
      background: #3498db;
      color: white;
      border-radius: 4px;
      padding: 2px 6px;
      font-size: 0.9rem;
    }

    blockquote {
      background: #e8f0fe;
      border-left: 5px solid #3498db;
      padding: 1rem;
      margin: 1rem 0;
    }

    .section {
      margin-bottom: 2rem;
    }

    .emoji {
      font-size: 1.2rem;
    }

    a {
      color: #2c7ec7;
      text-decoration: none;
    }

    a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>

  <h1>🚀 Employee Leave Tracker System</h1>
  <p>Welcome to the <strong>Employee Leave Tracker System</strong>! This is a Java-based console application designed to manage employee leave requests efficiently.</p>

  <blockquote>
    Built with <strong>Hibernate</strong> for database operations and <strong>MySQL</strong> for data storage. Features role-based access, robust input validation, and Gmail-only email support.
  </blockquote>

  <div class="section">
    <h2>📖 Overview</h2>
    <ul>
      <li><strong>Employees</strong> can apply for leaves (Casual, Sick, Earned), view their leave history, and check balances.</li>
      <li><strong>Managers</strong> can view and approve/reject pending leave requests.</li>
      <li><strong>Admins</strong> can credit leave balances and view all users.</li>
    </ul>
    <p><strong>Note:</strong> Gmail addresses with a single @ symbol are enforced for registration and login.</p>
  </div>

  <div class="section">
    <h2>✨ Features</h2>
    <ul>
      <li><span class="emoji">🧑‍💼</span> <strong>Employee:</strong> Apply for leaves, view leave history, and check balances.</li>
      <li><span class="emoji">👨‍💼</span> <strong>Manager:</strong> Approve or reject requests with comments.</li>
      <li><span class="emoji">🛠️</span> <strong>Admin:</strong> Credit balances and view users.</li>
      <li><span class="emoji">📅</span> Leave Types: Casual, Sick, Earned with tracking.</li>
      <li><span class="emoji">✅</span> Input Validation for all user inputs.</li>
      <li><span class="emoji">📧</span> Gmail-only email support with strict validation.</li>
      <li><span class="emoji">🗄️</span> Hibernate + MySQL integration.</li>
      <li><span class="emoji">🚫</span> Prevents overlapping leave requests.</li>
    </ul>
  </div>

  <div class="section">
    <h2>🛠️ Setup Instructions</h2>
    <h3>📋 Prerequisites</h3>
    <ul>
      <li>Java 8+ ☕</li>
      <li>MySQL Server 🗄️</li>
      <li>Maven or Gradle 📦</li>
      <li>Git 📥</li>
    </ul>

    <h3>📂 Steps</h3>

    <ol>
      <li><strong>Clone the Repository:</strong>
        <pre><code>git clone https://github.com/your-username/employee-leave-tracker.git
cd employee-leave-tracker</code></pre>
      </li>

      <li><strong>Set Up MySQL Database:</strong>
        <pre><code>
-- Create database
CREATE DATABASE leave_tracker;

-- Add leave_type column
ALTER TABLE leave_requests ADD leave_type ENUM('CASUAL', 'SICK', 'EARNED');
UPDATE leave_requests SET leave_type = 'CASUAL' WHERE leave_type IS NULL;
        </code></pre>

        <p>Update <code>hibernate.cfg.xml</code> with:</p>
        <pre><code>&lt;property name="hibernate.connection.url"&gt;jdbc:mysql://localhost:3306/leave_tracker&lt;/property&gt;
&lt;property name="hibernate.connection.username"&gt;your-username&lt;/property&gt;
&lt;property name="hibernate.connection.password"&gt;your-password&lt;/property&gt;
        </code></pre>
      </li>

      <li><strong>Install Dependencies (Maven):</strong>
        <pre><code>&lt;dependencies&gt;
  &lt;dependency&gt;
    &lt;groupId&gt;org.hibernate&lt;/groupId&gt;
    &lt;artifactId&gt;hibernate-core&lt;/artifactId&gt;
    &lt;version&gt;5.6.15.Final&lt;/version&gt;
  &lt;/dependency&gt;
  &lt;dependency&gt;
    &lt;groupId&gt;mysql&lt;/groupId&gt;
    &lt;artifactId&gt;mysql-connector-java&lt;/artifactId&gt;
    &lt;version&gt;8.0.33&lt;/version&gt;
  &lt;/dependency&gt;
&lt;/dependencies&gt;
        </code></pre>
      </li>

      <li><strong>Build and Run:</strong>
        <pre><code>mvn clean install
mvn clean package
java -jar target/employee-leave-tracker.jar</code></pre>
      </li>
    </ol>
  </div>

  <div class="section">
    <h2>🚀 Usage</h2>

    <h3>🔑 Main Menu</h3>
    <pre><code>Employee Leave Tracker System
1. Login
2. Register
3. Exit
Choose an option:</code></pre>

    <h3>📝 Register</h3>
    <pre><code>Enter name: John Doe
Enter email (must be Gmail): john.doe@gmail.com
Enter password: password123
Enter role (EMPLOYEE/MANAGER/ADMIN): EMPLOYEE
Registration successful!</code></pre>

    <h3>🔐 Login</h3>
    <pre><code>Enter email: john.doe@gmail.com
Enter password: ******
=> Role-based menu displayed.</code></pre>
  </div>

  <div class="section">
    <h2>🛠️ Admin Features</h2>
    <pre><code>Admin Menu:
1. Credit Leave Balance
2. View All Users
3. Logout</code></pre>

    <h4>✔️ Credit Balance</h4>
    <pre><code>Enter email: john.doe@gmail.com
Casual leave: 5
Sick leave: 3
Earned leave: 2
=> Leave balance updated!</code></pre>

    <h4>📋 View Users</h4>
    <pre><code>ID: 1, Name: John, Email: john.doe@gmail.com, Role: EMPLOYEE
ID: 2, Name: Manager, Email: m@gmail.com, Role: MANAGER
ID: 3, Name: Admin, Email: admin@gmail.com, Role: ADMIN</code></pre>
  </div>

  <div class="section">
    <h2>👨‍💼 Manager Features</h2>
    <pre><code>Manager Menu:
1. View Pending Requests
2. Approve/Reject Request
3. Logout</code></pre>

    <h4>✅ Approve Example</h4>
    <pre><code>Enter request ID: 1
Action: APPROVED
Comment: OK
=> Request approved</code></pre>
  </div>

  <div class="section">
    <h2>🧑‍💼 Employee Features</h2>
    <ul>
      <li>Apply for a leave</li>
      <li>View leave history</li>
      <li>Check leave balance</li>
    </ul>
  </div>

  <div class="section">
    <h2>🐞 Error Handling</h2>
    <ul>
      <li><strong>Invalid Email:</strong> Only Gmail addresses allowed.</li>
      <li><strong>Invalid Inputs:</strong> Handled gracefully with prompts.</li>
      <li><strong>Leave Overlaps:</strong> Prevents duplicate/overlapping requests.</li>
      <li><strong>Insufficient Balance:</strong> Leave denied if not enough days.</li>
    </ul>
  </div>

  <div class="section">
    <h2>🤝 Contributing</h2>
    <ol>
      <li>Fork the repository 🍴</li>
      <li>Create a feature branch: <code>git checkout -b feature/your-feature</code></li>
      <li>Commit changes: <code>git commit -m "Add your feature"</code></li>
      <li>Push: <code>git push origin feature/your-feature</code></li>
      <li>Open a Pull Request 📬</li>
    </ol>
  </div>

  <div class="section">
    <h2>📜 License</h2>
    <p>Licensed under the MIT License. See the LICENSE file for details.</p>
  </div>

  <div class="section">
    <h2>📬 Contact</h2>
    <p>Questions or suggestions? Open an issue or email <a href="mailto:bdash3339@gmail.com">bdash3339@gmail.com</a></p>
  </div>

  <h3 style="text-align:center;">🎉 Happy Leave Tracking! 🎉</h3>

</body>
</html>
