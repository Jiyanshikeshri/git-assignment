package com.example.session3.model;

// Simple User class to store user details
public class User {

    private int id;
    private String name;
    private int age;
    private String role;

    // Constructor to initialize user
    public User(int id, String name, int age, String role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.role = role;
    }

    // getters
    public int getId(){ 
        return id; 
    }

    public String getName(){
        return name; 
    }

    public int getAge(){
        return age; 
    }
    
    public String getRole(){ 
        return role; 
    }
}