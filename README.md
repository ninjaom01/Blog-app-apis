 <H1> Project Overview </H1>

Blogging App APIs is a Spring Boot-based backend application that powers a blogging and social media-style platform.
It supports user authentication, post management (CRUD), categories, comments, and secure REST APIs using Spring Security with JWT.
The entire API is documented with Swagger (OpenAPI 3) for easy testing.

 <H1> Features </H1>

 User Registration & Login (with JWT Authentication)
 Create, Update, Delete, and View Blog Posts
 Category Management (assign posts to categories)
 Comment System (add and delete comments)
 User Roles (Admin/User) — role-based API access
 Spring Security + JWT for secure API access
 Swagger API Documentation for testing and exploring endpoints

<H1> Tech Stack </H1>
Technology	Description :
Spring Boot 3.3.4	Core backend framework
Spring Data JPA	ORM layer for MySQL
Spring Security + JWT	Authentication & authorization
Hibernate	Database ORM provider
MySQL	Relational database
Swagger (Springdoc OpenAPI 2.6.0)	Interactive API documentation
ModelMapper	DTO ↔ Entity mapping
Lombok	Reduces boilerplate code
Java 21	Programming language
<H1> How to Run the Project Locally </H1>

1. Clone the Repository
git clone [https://github.com/ninjaom01/Blogging-app.git](https://github.com/ninjaom01/Blogging-app.git)
cd blogging-app

2. Prerequisites

Java 21

Maven 3+

MySQL 8+

3. Database Setup

In MySQL, create the database:

CREATE DATABASE blogging_app;


Then open src/main/resources/application.properties and set:

spring.datasource.url=jdbc:mysql://localhost:3306/blogging_app
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

4. Build and Run the App
mvn clean install
mvn spring-boot:run


The app starts on:
 http://localhost:9090

 Authentication Guide
1. Register User

POST /api/auth/register

Request Body (JSON):

{
  "name": "Govind",
  "email": "govind@gmail.com",
  "password": "password123",
  "about": "Spring developer and blogger"
}

2. Login User

POST /api/auth/login

Request Body:

{
  "username": "govind@gmail.com",
  "password": "password123"
}


Response:

{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}

3. Use JWT Token for Authenticated Requests

Add the token in your request header:

Authorization: Bearer <JWT_TOKEN>


Now you can access protected routes like:

POST /api/posts/

DELETE /api/posts/{id}

GET /api/users/

<H1> Swagger API Documentation</H1>

For UI:

(http://localhost:9090/swagger-ui/index.html)<br>
For Documentation:

(http://localhost:9090/v3/api-docs)


