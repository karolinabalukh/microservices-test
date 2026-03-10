# Microservices Test Project

This project contains two small Spring Boot microservices that communicate with each other and run using **Docker Compose**.

## The system consists of:

- **auth-api** – authentication service 
- **data-api** – data processing service
- **PostgreSQL** – database storing users and processing logs

Flow:

Client → **auth-api** → **data-api** → **Postgres**

The `auth-api` service authenticates the user, calls `data-api` for text transformation, saves the request result into PostgreSQL, and returns the result to the client.

---



---

## Build services

```bash
mvn -f auth-api/pom.xml clean package -DskipTests
mvn -f data-api/pom.xml clean package -DskipTests
```

Run with Docker
```
docker compose up --build
```

Services will start:
- auth-api   http://localhost:8080
- data-api   http://localhost:8081
- postgres   localhost:5432

---
# Services

## auth-api

Responsibilities:

- User registration
- User login
- JWT authentication
- Protected `/process` endpoint
- Calling `data-api`
- Saving processing logs to PostgreSQL

Endpoints:
- POST /api/auth/register
- POST /api/auth/login
- POST /api/process

---

## data-api

Responsibilities:

- Accept requests only from `auth-api`
- Validate internal token
- Process input text

Endpoint:
- POST /api/transform
 
Header required:
X-Internal-Token


---

## API Usage
### Register
POST http://localhost:8080/api/auth/register

Body:

{
"email": "test@test.com",
"password": "123456"
}
Example

### Login
POST http://localhost:8080/api/auth/login

Body:
{
"email": "test@test.com",
"password": "123456"
}

Response:
{
"token": "JWT_TOKEN"
}


### Process request
POST http://localhost:8080/api/process

Headers:

Authorization: Bearer <token>

Body:
{
"text": "hello"
}

Response:
{
"result": "HELLO"
}
