package com.example.session3.model;

// class to accept request body data
public class SubmitRequest {

    private String name;
    private Integer age;
    private String role;

    // getters and setters (important for request body)
    public String getName(){
        return name; 
    }

    public void setName(String name){
        this.name = name; 
    }

    public Integer getAge(){
        return age; 
    }

    public void setAge(Integer age){
        this.age = age; 
    }

    public String getRole(){
        return role; 
    }

    public void setRole(String role){
        this.role = role; 
    }
}