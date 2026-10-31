# 🐞 Bug Tracking System – Backend

A full-stack Bug Tracking System backend built using **Java Spring Boot** and **PostgreSQL**. It provides REST APIs for authentication, project management, bug tracking, and dashboard operations.

## 🚀 Technologies Used

- **Java** – Backend programming language
- **Spring Boot** – Backend framework
- **Spring Security** – Application security
- **JWT** – Token-based authentication
- **Spring Data JPA / Hibernate** – Database operations and ORM
- **PostgreSQL** – Relational database
- **Maven** – Dependency management and build tool

## ✨ Features

- **Authentication** – Secure user login using JWT authentication.
- **Project Management** – Manage projects.
- **Bug Management** – Create and manage bugs.
- **Bug Assignment** – Assign bugs to users.
- **Dashboard** – Access bug tracking statistics and project information.
- **REST APIs** – Provides API endpoints for frontend integration.

## 🏗️ Project Architecture

The backend follows a layered architecture:

- Controller Layer – Handles incoming API requests.
- Service Layer – Contains business logic.
- Repository Layer – Handles database operations.
- Entity Layer – Defines database entities.
- Security Layer – Manages authentication and authorization.

## ⚙️ Getting Started

### Prerequisites

- Java JDK
- Maven
- PostgreSQL
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/Mounisha025/bug-tracker-system.git
```

### 2. Navigate to the Project

```bash
cd bug-tracker-system
```

### 3. Configure Environment Variables

Configure your database credentials and mail password using environment variables.

```properties
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
MAIL_PASSWORD=your_mail_app_password
```

Never upload actual passwords or secrets to GitHub.

### 4. Configure PostgreSQL

Create a PostgreSQL database and configure the database connection in `application.properties`.

### 5. Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

The backend will start on the configured application port.

## 🔗 Frontend Repository

[Bug Tracking System – Frontend](https://github.com/Mounisha025/bug-tracker-system-frontend)

## 👩‍💻 Developer

**Padala Mounisha**

GitHub: [@Mounisha025](https://github.com/Mounisha025)

---

⭐ If you find this project interesting, feel free to explore the repository!
