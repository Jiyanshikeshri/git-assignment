package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    // constructor injection
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // Method to save a todo
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    // Method to get all todos
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    //Method to get all todos by id
    public Todo getTodoById(Long id) {
    return todoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    //Method to update the todos
    public Todo updateTodo(Long id, Todo updatedTodo) {

    Todo existing = todoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Todo not found"));

    // update fields
    existing.setTitle(updatedTodo.getTitle());
    existing.setDescription(updatedTodo.getDescription());
    existing.setStatus(updatedTodo.getStatus());

    return todoRepository.save(existing);
    }

    // method to delete todo
    public void deleteTodo(Long id) {
    if (!todoRepository.existsById(id)) {
        throw new RuntimeException("Todo not found with id: " + id);
    }
    todoRepository.deleteById(id);
    }
}