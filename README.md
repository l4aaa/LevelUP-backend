# 🚀 LevelUp – Backend API

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-green.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18+-336791.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

The robust server-side architecture for **LevelUp**, a platform that gamifies the student experience. This RESTful API orchestrates user progression, secure authentication, and real-time task management using a modern Java stack.

---

## ✨ Key Features

### 🎮 Gamification Engine
* **XP & Leveling System**: Calculates experience points and manages level thresholds (100 XP per level).
* **Achievement Unlocks**: Automatically evaluates user stats to unlock badges based on criteria like `TASK_COUNT`, `STREAK_DAYS`, and `XP_TOTAL`.
* **Global Leaderboard**: Optimized queries to rank users across different study programs.

### 🧠 Intelligent Task Management
* **Smart Assignment**: Assigns a daily mix of program-specific tasks (e.g., Law, CS) and general well-being quests.
* **Daily Rotation**: Automated logic clears unfinished tasks and assigns fresh ones upon the first login of a new day.
* **Async Verification**: Uses non-blocking threads (`@Async`) to simulate "grading" tasks, keeping the API responsive while processing happens in the background.

### 🔐 Security & Architecture
* **Stateless Auth**: Full JWT (JSON Web Token) implementation with custom filters.
* **Data Integrity**: Uses **Pessimistic Locking** (`PESSIMISTIC_WRITE`) to prevent race conditions during XP updates.
* **Robust Validation**: Jakarta Validation for all incoming request bodies.

---

## 🛠️ Tech Stack

* **Core**: Java 21, Spring Boot 4.0 (Web, Security, Data JPA)
* **Database**: PostgreSQL 18+
* **Security**: Spring Security, JJWT (0.13.0), BCrypt
* **Build Tool**: Maven 3.9+
* **Testing**: JUnit 5, MockMvc

---

## 📡 API Endpoints

### 🟢 Authentication
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/api/auth/register` | Register a new student and assign initial tasks. |
| **POST** | `/api/auth/login` | Authenticate user, update streaks, and return JWT. |
| **GET** | `/api/auth/study-programs` | List all available faculties/majors. |

### 🟡 Core Features
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/api/dashboard` | Get current user stats, level progress, and daily tasks. |
| **POST** | `/api/tasks/{id}/complete` | Submit a task. Triggers background verification thread. |
| **GET** | `/api/user/me` | Fetch full user profile and unlocked achievements. |
| **GET** | `/api/user/leaderboard` | Retrieve the global ranking of top students. |
| **GET** | `/api/user/achievements` | List all available achievements in the game. |

---

## 🚀 Getting Started

### 1. Prerequisites
* **Java JDK 21** installed.
* **PostgreSQL** installed and running on port `5432`.

### 2. Database Setup
Create a local database named `levelup_db`.
```bash
createdb levelup_db
```
*Note: The application is configured to validate the schema but NOT auto-generate it (`ddl-auto=none`). You must import the provided SQL seed file first.*

### 3. Configuration
Open `src/main/resources/application.properties` and update your database credentials:

```properties
spring.datasource.username=your_postgres_user
spring.datasource.password=your_postgres_password

# JWT Settings (Ensure secret is 32+ chars)
jwt.secret=your_super_secret_key_that_is_very_long
```

### 4. Running the Application
Use the included Maven Wrapper to run the app without installing Maven globally:

```bash
# Linux/macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

The server will start on `http://localhost:8080`.

---

## 📂 Project Structure

```bash
com.levelup.backend
├── controller       # REST Controllers (API Layer)
├── service          # Business Logic (Gamification, Auth, Async Tasks)
├── repository       # JPA Interfaces (Database Access)
├── entity           # Database Models (User, Task, Achievement)
├── security         # JWT Filters & Spring Security Config
└── dto              # Data Transfer Objects (Requests/Responses)
```

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/NewFeature`)
3. Commit your changes (`git commit -m 'Add some NewFeature'`)
4. Push to the branch (`git push origin feature/NewFeature`)
5. Open a Pull Request

## 📝 License

Distributed under the MIT License.