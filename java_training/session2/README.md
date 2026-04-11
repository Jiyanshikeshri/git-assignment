# Spring Boot Assignment - Session 2

## Project Overview

This is a Spring Boot project developed as part of Java training assignment.

The project demonstrates:

* REST API development
* Layered Architecture
* Dependency Injection
* Spring Core concepts

##  Tech Stack

* Java 17
* Spring Boot
* Maven

##  Project Structure

* controller → Handles API requests
* service → Contains business logic
* repository → Handles data storage (in-memory)
* model → Defines data structure
* component → Reusable components

##  APIs Implemented

### 1. User Management

* GET /users → Fetch all users
* POST /users → Create user
* GET /users/{id} → Fetch user by ID

### 2. Notification System

* GET /notify → Trigger notification

### 3. Message Formatter

* GET /message?type=SHORT
* GET /message?type=LONG

##  Concepts Used

* IoC (Inversion of Control)
* Dependency Injection (Constructor-based)
* @Component, @Service, @Repository, @RestController
* Layered Architecture

## Output

* APIs returning JSON responses
* Dynamic message formatting working
* Notification API working
