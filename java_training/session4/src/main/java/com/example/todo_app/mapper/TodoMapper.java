package com.example.todo_app.mapper;

import com.example.todo_app.dto.TodoDTO;
import com.example.todo_app.model.Todo;
import com.example.todo_app.model.Status;

import java.time.LocalDateTime;

// This class is used to convert DTO to Entity
public class TodoMapper {

    // Convert DTO to Entity (for saving in DB)
    public static Todo toEntity(TodoDTO dto) {

        Todo todo = new Todo();

        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());

        // set default status
        todo.setStatus(Status.PENDING);

        return todo;
    }

    // Convert Entity to DTO (for sending response)
    public static TodoDTO toDTO(Todo todo) {

        TodoDTO dto = new TodoDTO();

        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());

        return dto;
    }
}