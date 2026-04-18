package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    // Method to save a todo
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    // Method to get all todos
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
}