📚 Library Management System

A full-stack Library Management System built using Spring Boot, Java, MySQL, HTML, CSS, and JavaScript.
This project provides a complete solution for managing books, users, and book issue/return operations with authentication and role-based access.

🚀 Features
👤 User Management

User Registration & Login

Role-based access (Admin / User)

Secure authentication using Spring Security

📖 Book Management (Admin)

Add new books

Update book details

Delete books

View all available books

🔄 Issue & Return System

Issue books to users

Return books

Track issued books

Prevent issuing unavailable books

🧾 Dashboard

Admin Dashboard for managing library operations

User Dashboard for viewing issued books

🎨 Frontend

Responsive UI using HTML, CSS, and JavaScript

Separate pages for login, registration, book listing, and dashboards

🛠️ Technologies Used
Backend

Java 8+

Spring Boot

Spring MVC

Spring Data JPA (Hibernate)

Spring Security

RESTful APIs

MySQL

Frontend

HTML5

CSS3

JavaScript

Tools

Eclipse / IntelliJ IDEA

Maven

Postman (API testing)

Git & GitHub

📂 Project Structure
Library-Management-System
│
├── src/main/java
│   └── com.example.library
│       ├── controller
│       ├── service
│       ├── repository
│       ├── entity
│       ├── security
│       └── LibraryApplication.java
│
├── src/main/resources
│   ├── static
│   │   ├── css
│   │   ├── js
│   │   └── images
│   ├── templates
│   │   ├── login.html
│   │   ├── register.html
│   │   ├── dashboard.html
│   │   └── books.html
│   └── application.properties
│
├── pom.xml
└── README.md

🗄️ Database Configuration

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

▶️ How to Run the Project
Step 1: Clone the Repository
git clone https://github.com/your-username/Library-Management-System.git

Step 2: Import into IDE

Open Eclipse

File → Import → Existing Maven Project

Select the project folder

Step 3: Create Database
CREATE DATABASE library_db;

Step 4: Run the Application

Run LibraryApplication.java as Spring Boot App

Step 5: Access the App
http://localhost:8080

🔐 Default Roles
Role	Access
Admin	Full access (Book CRUD, Issue/Return)
User	View books, Issue/Return books
📌 Future Enhancements

JWT-based authentication

Pagination & search

Email notifications

Book fine calculation

API documentation using Swagger

Docker support

🤝 Contributing

Contributions are welcome!
Feel free to fork the repository and submit a pull request.

📄 License

This project is for learning and educational purposes.

👨‍💻 Author

Rohit
Java & Spring Boot Developer
