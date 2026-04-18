
package com.example.todo_app.controller;
import java.util.List;

import com.example.todo_app.dto.TodoDTO;
import com.example.todo_app.mapper.TodoMapper;
import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    // constructor injection
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    //POST API to create todo
    @PostMapping
    public TodoDTO createTodo(@Valid @RequestBody TodoDTO dto) {
        // DTO to Entity
        Todo todo = TodoMapper.toEntity(dto);
        // save to DB
        Todo savedTodo = todoService.createTodo(todo);
        // Entity to DTO
        return TodoMapper.toDTO(savedTodo);
    }

    //GET API to get all the todo
    @GetMapping
    public List<TodoDTO> getAllTodos() {

    List<Todo> todos = todoService.getAllTodos();

    return todos.stream()
            .map(TodoMapper::toDTO)
            .toList();
    }
}