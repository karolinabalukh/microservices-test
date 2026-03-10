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

### 1. Register

* **URL:** `http://localhost:8080/api/auth/register`
* **Method:** `POST`
* **Headers:** `Content-Type: application/json`

**Request Body:**
```json
{
  "email": "test@test.com",
  "password": "111111"
}
```

### 2. Login

* **URL:** `http://localhost:8080/api/auth/login`
* **Method:** `POST`
* **Headers:** `Content-Type: application/json`

**Request Body:**
```json
{
  "email": "test@test.com",
  "password": "111111"
}
Response (200 OK):

JSON
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Process Request 

* **URL:** `http://localhost:8080/api/process`
* **Method:** `POST`
* **Headers:**
  * `Content-Type: application/json`
  * `Authorization: Bearer <jwt_token>`

**Request Body:**
```json
{
  "text": "hello"
}
Response (200 OK):

JSON
{
  "result": "HELLO"
}
```
