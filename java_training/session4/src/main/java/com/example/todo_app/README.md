# Todo App (Spring Boot)

## Features
- Create Todo
- Get All Todos
- Get Todo by ID
- Update Todo
- Delete Todo

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database

## APIs

### 1. Create Todo
POST /todos

### 2. Get All Todos
GET /todos

### 3. Get Todo by ID
GET /todos/{id}

### 4. Update Todo
PUT /todos/{id}

### 5. Delete Todo
DELETE /todos/{id}

## Notes
- H2 in-memory DB used
- Data resets on restart
- DTO + Validation used
- Exception handling implemented
