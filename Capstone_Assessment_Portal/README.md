# Assessment Portal

A full-stack web-based **Assessment Portal** developed using **FastAPI**, **ReactJS**, and **MongoDB**. The application enables administrators to create and manage assessments while allowing students to attempt quizzes and view their results. The project follows a modular architecture with secure authentication, role-based authorization, automated testing, and RESTful APIs.

---

## Project Overview

The Assessment Portal is designed to streamline the complete assessment lifecycle.

### Admin can:
- Manage Categories
- Manage Quizzes
- Manage Questions
- View Student Results
- Monitor Assessment Performance

### Student can:
- Register and Login
- Browse Available Categories
- Attempt Quizzes
- View Result History
- View Detailed Result Breakdown

---

# Technology Stack

## Backend
- FastAPI
- Python 3.11
- MongoDB Atlas
- PyMongo
- JWT Authentication
- RSA Password Encryption
- Passlib (bcrypt)
- Pydantic

## Frontend
- ReactJS
- React Router DOM
- Axios
- CSS

## Testing
- Pytest
- FastAPI TestClient
- pytest-cov

## Tools
- Swagger UI
- Git
- GitHub
- VS Code

---

# Features

## Authentication
- Student Registration
- Login
- JWT Access Token
- Refresh Token
- Role Based Authorization
- RSA Password Encryption
- Password Hashing using bcrypt

---

## Category Management
- Create Category
- Update Category
- Delete Category
- View Categories

---

## Quiz Management
- Create Quiz
- Update Quiz
- Delete Quiz
- View All Quizzes

---

## Question Management
Supports

- MCQ Questions
- True/False Questions

Features

- Add Question
- Update Question
- Delete Question
- View Questions

---

## Quiz Attempt

- Start Quiz
- Resume Attempt
- Save Partial Answers
- Auto Submit on Expiry
- Attempt Limit
- Question Snapshot
- Time Bound Assessment

---

## Result Management

- Automatic Evaluation
- Percentage Calculation
- Pass / Fail Status
- Result History
- Latest Result
- Detailed Result Breakdown
- Admin Result Dashboard

---

# Security Features

- JWT Authentication
- Role Based Access Control (RBAC)
- RSA Public/Private Key Encryption
- Password Hashing using bcrypt
- Protected APIs
- Input Validation using Pydantic

---

# Project Structure

```
Assessment_Portal
│
├── backend
│   ├── app
│   │   ├── config
│   │   ├── constants
│   │   ├── exceptions
│   │   ├── middleware
│   │   ├── repositories
│   │   ├── routers
│   │   ├── schemas
│   │   ├── services
│   │   ├── utils
│   │   └── main.py
│   │
│   ├── keys
│   ├── tests
│   └── requirements.txt
│
├── frontend
│   ├── src
│   │   ├── components
│   │   ├── context
│   │   ├── pages
│   │   ├── routes
│   │   ├── services
│   │   ├── utils
│   │   └── App.jsx
│   │
│   └── package.json
│
└── README.md
```

---

# Database Collections

The application uses MongoDB Atlas.

Collections

- users
- categories
- quizzes
- questions
- attempts
- results

---

# Installation

## Clone Repository

```bash
git clone <repository-url>
```

---

## Backend Setup

```bash
cd backend

python -m venv venv
```

Activate Virtual Environment

### Windows

```bash
venv\Scripts\activate
```

### Install Dependencies

```bash
pip install -r requirements.txt
```

### Run Backend

```bash
uvicorn app.main:app --reload
```

Backend

```
http://127.0.0.1:8000
```

Swagger

```
http://127.0.0.1:8000/docs
```

---

## Frontend Setup

```bash
cd frontend
```

Install packages

```bash
npm install
```

Run application

```bash
npm run dev
```

Frontend

```
http://localhost:5173
```

---

# Environment Variables

Create a `.env` file inside the backend.

Example

```env
MONGODB_URL=your_mongodb_connection_string

DATABASE_NAME=assessment_portal

SECRET_KEY=your_secret_key

ALGORITHM=HS256

ACCESS_TOKEN_EXPIRE_MINUTES=30

REFRESH_TOKEN_EXPIRE_DAYS=7
```

---

# Authentication Flow

1. Frontend requests RSA Public Key.
2. Backend returns the Public Key.
3. Password is encrypted on the client using RSA.
4. Encrypted password is sent to the backend.
5. Backend decrypts the password using the Private Key.
6. Password is verified using bcrypt.
7. JWT Access Token and Refresh Token are generated.
8. Protected APIs are accessed using the Access Token.

---

# User Roles

## Admin

- Manage Categories
- Manage Quizzes
- Manage Questions
- View Dashboard
- View Student Results

---

## Student

- Register
- Login
- Browse Categories
- Attempt Quizzes
- Resume Quiz
- Submit Quiz
- View Results

---

# API Testing

Automated API testing has been implemented using **Pytest** and **FastAPI TestClient**.

Run all test cases

```bash
pytest -v
```

Generate Coverage Report

```bash
pytest --cov=app --cov-report=term-missing
```

Generate HTML Coverage

```bash
pytest --cov=app --cov-report=html
```

---

# Test Summary

- Total Automated Test Cases: **63**
- Test Framework: **Pytest**
- API Testing: **FastAPI TestClient**
- Overall Backend Coverage: **88%**
- All Test Cases Passed Successfully

---

# API Documentation

FastAPI automatically generates interactive API documentation.

Swagger UI

```
http://127.0.0.1:8000/docs
```

ReDoc

```
http://127.0.0.1:8000/redoc
```

---

# Implemented Modules

- Authentication
- Category Management
- Quiz Management
- Question Management
- Quiz Attempt
- Result Management
- Admin Dashboard
- Student Dashboard

---

# Future Enhancements

- Email Notifications
- Leaderboard
- Scheduled Quizzes
- Randomized Questions
- Negative Marking
- Analytics Dashboard
- Question Import using Excel
- Timer Synchronization
- AI-based Question Generation

---

# Author

**Jiyanshi Keshri**

# Project Highlights

- Full Stack Application
- Modular FastAPI Architecture
- React Frontend
- MongoDB Integration
- JWT Authentication
- RSA Password Encryption
- Role Based Access Control
- Automated Quiz Evaluation
- Resume Quiz Functionality
- RESTful APIs
- Swagger Documentation
- Automated API Testing
- 88% Backend Code Coverage

---

## License

This project was developed for educational and learning purposes as part of a Full Stack Capstone Project