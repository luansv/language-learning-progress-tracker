# Language Learning Progress Tracker

A Spring Boot RESTful API for tracking language learning progress, including user registration, vocabulary management, lessons, and languages.
It supports relationships like:
- Each user can have multiple lessons and vocabulary entries.
- Each lesson and vocabulary item can be associated with multiple languages.

---

## Features

- User Registration and Management
- Authentication (JWT)
- Vocabulary CRUD
- Lesson CRUD
- Language CRUD
- Filter vocabulary and lessons by language(s)
- Aggregated user overview endpoint
- Unit testing with JUnit + Mockito
- Redis/MySQL & Docker support
- H2 in-memory database for dev/testing

---

##  Tech Stack

| Layer            | Technology                     |
|------------------|--------------------------------|
| Language         | Java 17                        |
| Framework        | Spring Boot 3.x                |
| Database         | MySQL / H2                     |
| Caching          | Redis via Spring Cache         |
| Auth             | Spring Security + JWT          |
| ORM              | Hibernate / JPA                |
| Testing          | JUnit 5 + Mockito              |
| Containerization | Docker, Docker Compose         |
| Build Tool       | Maven                          |

---


## Setup

### 1. Clone the repository:

   ```bash
   git clone git clone https://github.com/yourusername/language-learning-progress-tracker.git
   cd language-learning-progress-tracker
   ```

### 2. Run with Docker (Recommended):

   ```bash
   docker-compose up --build
   ```

### 3. Run locally without Docker

```bash
./mvnw spring-boot:run
```

Make sure your `application.properties` is configured with either H2 or MySQL.

---

##  Authentication

- **Register**: `POST /api/user/register`
- **Login**: `POST /api/auth/login` → returns a JWT
- **Authorization**: Add JWT token to headers:

```
Authorization: Bearer <token>
```

---

##  Redis Caching

The following methods use Redis-backed caching:

- `GET /api/users/{id}/overview`
- `GET /api/languages`
- Vocab + lesson filters by language (optional)

To configure Redis:

```properties
# Redis
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
```

> Redis container is included in `docker-compose.yml`.

---

##  Key Endpoints

###  - User

| Method | Endpoint                   | Description         |
|--------|----------------------------|---------------------|
| POST   | `/api/user/register`       | Register            |
| POST   | `/api/auth/login`          | Authenticate        |
| GET    | `/api/users/{id}/overview` | Full user overview  |
| GET    | `/api/users/all`           | List all users      |

###  - Vocabulary

| Method | Endpoint                         | Description              |
|--------|----------------------------------|--------------------------|
| POST   | `/api/users/{userId}/vocab`      | Add vocab                |
| GET    | `/api/users/{userId}/vocab`      | Get all/filter by lang   |
| GET    | `/api/users/{userId}/vocab/{id}` | Get by ID                |

### - Lessons

| Method | Endpoint                          | Description             |
|--------|-----------------------------------|-------------------------|
| POST   | `/api/users/{userId}/lessons`     | Add lesson              |
| GET    | `/api/users/{userId}/lessons`     | Get all/filter by lang  |

###  - Languages

| Method | Endpoint         | Description        |
|--------|------------------|--------------------|
| POST   | `/api/languages` | Create language    |
| GET    | `/api/languages` | List all (cached)  |

---

##  Tests

```bash
./mvnw test
```

Covers:
- Services
- DTO mapping
- Cache testing
- Exception handling

---

##  Docker Overview

### `docker-compose.yml`

```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db
      - redis
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/language_db
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
      SPRING_REDIS_HOST: redis

  db:
    image: mysql:8
    environment:
      MYSQL_DATABASE: language_db
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"

  redis:
    image: redis:7
    ports:
      - "6379:6379"
```

