package com.example.todo_app.dto;

import jakarta.validation.constraints.NotBlank;

// This is used to take input from user
public class TodoDTO {

    // Title should not be null and must have at least 3 characters
    @NotNull(message = "Title cannot be null")
    @Size(min = 3, message = "Title must be at least 3 characters")
    private String title;

    // Description should not be empty
    @NotBlank(message = "Description is required")
    private String description;

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }
}