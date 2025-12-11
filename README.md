# 🚀 LevelUp Backend

The backend API for **LevelUp**, a gamified learning platform. This Spring Boot service manages authentication, user progression, study program data, daily tasks, XP, levels, achievements, and an asynchronous task verification system.

---

## 📋 Table of Contents
- [Overview](#-overview)
- [Tech Stack](#-tech-stack)
- [Key Features](#-key-features)
- [Architecture--Async-Flow](#-architecture--async-flow)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Configuration](#-configuration)

---

## 📖 Overview

LevelUp transforms studying into a game. Users choose a study program, complete tasks, and gain XP that levels up their profile. The backend manages all core logic, including secure JWT authentication, PostgreSQL persistence, and a background verification mechanism that simulates heavy grading tasks without blocking the UI.

---

## 🛠 Tech Stack

- **Language:** Java 21  
- **Framework:** Spring Boot (Web, Security, Data JPA)  
- **Database:** PostgreSQL  
- **Security:** JWT (JSON Web Tokens) + Spring Security  
- **Build Tool:** Maven (Wrapper included)  
- **Other:** Lombok, Async Processing (`@Async`)

---

## ✨ Key Features

### 🔐 Authentication & Security
- Stateless **JWT-based authentication**
- Password hashing using **BCrypt**
- CORS enabled for local development (`localhost:3000`, `localhost:5173`)
- Roles & authorization handled through Spring Security filters

### 🎮 Gamification Engine
- **XP System:** Completing tasks awards XP
- **Leveling:** XP thresholds determine user levels
- **Streak Tracking:** Daily login streaks tracked automatically
- **Achievements:** Automatic unlocking of badges (e.g., “Complete 10 tasks”)
- **Leaderboard:** Global XP-based ranking

### ⚡ Asynchronous Task Verification
- Task completion triggers an **async background process**
- Tasks move from `PENDING → VERIFYING → COMPLETED`
- Simulates real-world “heavy processing” (sleep delay)
- Ensures a fast, non-blocking API response
- Awards XP and checks achievements when verification finishes

---

## 🏗 Architecture & Async Flow

The backend follows a clean **Controller → Service → Repository** structure.

### 🔄 Task Completion Flow
1. **User submits a task**
   `POST /api/tasks/{id}/complete`
2. **Atomic update**
   Task status switches from `PENDING` → `VERIFYING` to avoid duplicates.
3. **Async service triggered**
   HTTP response returns immediately.
4. **Background process:**
   - Simulates heavy verification (3-second sleep)
   - Marks the task as `COMPLETED`
   - Awards XP, updates levels, checks achievements

This prevents UI lag and maintains responsiveness under load.

---

## 🚀 Getting Started

### ✅ Prerequisites
- Java **21**
- PostgreSQL running on **port 5432**
- Maven (optional; the wrapper is included)

---

### 🗄 Database Setup

1. Create the database:
   ```sql
   CREATE DATABASE levelup_db;
   ```
2. Apply your schema + seed data manually
   (Hibernate `ddl-auto` is set to `none` to avoid overwriting your own SQL):

   ```properties
   spring.jpa.hibernate.ddl-auto=none
   ```

> If this is your first time running the backend and you don’t have schema files applied, either import them manually or temporarily set:
>
> ```properties
> spring.jpa.hibernate.ddl-auto=update
> ```
>
> *(Not recommended for production.)*

---

### 🛠 Installation

#### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/levelup-backend.git
cd levelup-backend
```

#### 2. Configure Database & Secrets

Edit:

```
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/levelup_db
spring.datasource.username=postgres
spring.datasource.password=your_password

jwt.secret=your_jwt_secret
jwt.expiration=86400000
```

#### 3. Run the Server

```bash
./mvnw spring-boot:run
```

Backend starts at:

```
http://localhost:8080
```

---

## 📡 API Endpoints

### 🔐 Authentication

| Method | Endpoint                   | Description                   |
| ------ | -------------------------- | ----------------------------- |
| `POST` | `/api/auth/register`       | Register a new user           |
| `POST` | `/api/auth/login`          | Login and receive a JWT token |
| `GET`  | `/api/auth/study-programs` | Get all study programs        |

---

### 📊 Dashboard & Daily Tasks

| Method | Endpoint                   | Description                               |
| ------ | -------------------------- | ----------------------------------------- |
| `GET`  | `/api/dashboard`           | Retrieve XP, level, streak, today's tasks |
| `POST` | `/api/tasks/{id}/complete` | Submit a task for async verification      |

---

### 👤 User & Gamification

| Method | Endpoint                 | Description                                 |
| ------ | ------------------------ | ------------------------------------------- |
| `GET`  | `/api/user/me`           | Get the current user profile & achievements |
| `GET`  | `/api/user/leaderboard`  | Top users by XP                             |
| `GET`  | `/api/user/achievements` | All possible achievements                   |

---

## ⚙️ Configuration

Located in:

```
src/main/resources/application.properties
```

| Property                | Default                                     | Description                |
| ----------------------- | ------------------------------------------- | -------------------------- |
| `server.port`           | 8080                                        | Port to run backend        |
| `jwt.secret`            | —                                           | Secret key for JWT signing |
| `jwt.expiration`        | 86400000                                    | JWT lifetime in ms         |
| `spring.datasource.url` | jdbc:postgresql://localhost:5432/levelup_db | DB URI                     |
| `spring.jpa.show-sql`   | true                                        | Log SQL queries            |

---

## 🏁 Final Notes

* This backend is fully compatible with the LevelUp frontend.
* Ensure your DB schema is initialized before running the server.
* Replace default credentials & secrets before deploying.
* Async verification makes the user experience smoother and scalable.

**Built with ❤️ using Spring Boot.**

```
