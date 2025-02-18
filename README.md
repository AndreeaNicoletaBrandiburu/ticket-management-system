Final Project Report - Ticket Management System

Project Overview
This project is a Spring Boot Application developed to manage tickets and users efficiently. The application provides REST APIs for handling tickets, comments, users, authentication, and categories, ensuring proper business logic implementation and validation.

1. Compliance with Project Requirements

I. Define a System of Your Choice

10 Business Requirements Defined ✅

Document Created for MVP Phase ✅ (Contains a description of the main features)

Repository Created & Document Committed ✅ (GitHub Repository) (https://github.com/AndreeaNicoletaBrandiburu/ticket-management-system/edit/main/README.md)

II. Spring Boot Application Implementation

1. REST Endpoints Implementation ✅

Users API: CRUD operations for users

Tickets API: Ticket management

Comments API: Adding and managing comments

Authentication API: Login and registration

Categories API: Categorizing tickets

2. Beans for Services (Business Logic) ✅

Each feature has a dedicated service that implements its business logic.

3. Beans for Repositories (Database Access) ✅

Each entity has a corresponding repository to manage persistence.

4. Unit Testing (JUnit + Mockito) ✅

All REST endpoints and services are covered with unit tests, and all tests pass successfully.

5. Data Persistence with Database (H2/SQL) ✅

6 Entities Implemented:

User

Ticket

Comment

Category

Role

Notification

Relationships Established:

User - Ticket (Many-to-One)

Ticket - Comment (One-to-Many)

Ticket - Category (Many-to-One)

User - Role (Many-to-One)

6. POJO Validation ✅

Existing Validation Constraints:

Email format validation

Password length constraints

Non-null constraints

Custom Validation Implemented (if required)

7. Documentation with Swagger ✅

Swagger UI Available at http://localhost:8080/swagger-ui.html

Documentation Exported as JSON/YAML (swagger-docs.json)

8. Demonstration of Functionality ✅

Tested via Postman (Screenshots provided)

Swagger UI used for API Testing

Database Access via H2 Console (http://localhost:8080/h2-console) - now accessible

Final Submission Includes:

GitHub Repository with Full Codebase https://github.com/AndreeaNicoletaBrandiburu/ticket-management-system/

Swagger Documentation JSON/YAML
