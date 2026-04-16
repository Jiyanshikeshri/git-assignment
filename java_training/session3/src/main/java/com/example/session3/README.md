# Spring Boot Assignment - Session 3

## Project Overview

This project is developed as part of Spring and REST assignment.

It demonstrates:

* REST API development using Spring Boot
* Layered Architecture (Controller → Service → Repository)
* Dependency Injection (Constructor-based)
* Basic validation and filtering logic

## Tech Stack

* Java 17
* Spring Boot
* Maven

## Project Structure

* controller → Handles API requests
* service → Contains business logic
* repository → Stores data (in-memory)
* model → Defines data structure

## APIs Implemented

### 1. User Search API

GET /users/search

Query Parameters:

* name (optional)
* age (optional)
* role (optional)

Features:

* Returns all users if no parameter is passed
* Filters based on given parameters
* Case-insensitive matching for name and role
* Exact match for age

### 2. Submit API

POST /users/submit

* Accepts JSON input
* Performs basic validation
* Returns success or error message

### 3. Delete API with Confirmation

DELETE /users/{id}?confirm=true

* Deletes user only if confirm=true
* Returns message if confirmation is missing
