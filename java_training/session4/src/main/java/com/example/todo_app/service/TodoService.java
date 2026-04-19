package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.todo_app.exception.ResourceNotFoundException;
import com.example.todo_app.model.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.todo_app.service.NotificationServiceClient;

import java.util.List;

@Service
public class TodoService {

    // Logger for service layer
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository todoRepository;
    
    private final NotificationServiceClient notificationServiceClient;

    // constructor injection
    public TodoService(TodoRepository todoRepository,NotificationServiceClient notificationServiceClient) {
        this.todoRepository = todoRepository;
        this.notificationServiceClient = notificationServiceClient;
    }

    // Method to save a todo
    public Todo createTodo(Todo todo) {
        logger.info("Saving new TODO in database");
        Todo saved = todoRepository.save(todo);
        logger.info("TODO saved with id: {}", saved.getId());

        // Calling external notification service
        notificationServiceClient.sendNotification(
                "New TODO created with id: " + saved.getId()
        );

        return saved;
    }

    // Method to get all todos
    public List<Todo> getAllTodos() {
        logger.info("Fetching all TODOs");
        return todoRepository.findAll();
    }

    //Method to get all todos by id
    public Todo getTodoById(Long id) {
        logger.info("Fetching TODO with id: {}", id);
        return todoRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("TODO not found with id: {}", id);
                return new ResourceNotFoundException("Todo not found with id: " + id);
            });
    }

    //Method to update the todos
    public Todo updateTodo(Long id, Todo updatedTodo) {

    logger.info("Updating TODO with id: {}", id);

    Todo existing = todoRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("TODO not found for update with id: {}", id);
                return new ResourceNotFoundException("Todo not found with id: " + id);
            });

    // update fields
    existing.setTitle(updatedTodo.getTitle());
    existing.setDescription(updatedTodo.getDescription());
    // Status Validation Logic
    if (existing.getStatus() == Status.PENDING && updatedTodo.getStatus() == Status.COMPLETED) {
        existing.setStatus(Status.COMPLETED);
    } 
    else if (existing.getStatus() == Status.COMPLETED && updatedTodo.getStatus() == Status.PENDING) {
        existing.setStatus(Status.PENDING);
    } 
    else if (updatedTodo.getStatus() != existing.getStatus()) {
        throw new RuntimeException("Invalid status transition");
    }

    logger.info("TODO updated successfully with id: {}", id);

    return todoRepository.save(existing);
    }

    // method to delete todo
    public void deleteTodo(Long id) {

        logger.info("Deleting TODO with id: {}", id);
        
        if (!todoRepository.existsById(id)) {
            logger.error("TODO not found for deletion with id: {}", id);
            throw new ResourceNotFoundException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);

        logger.info("TODO deleted successfully with id: {}", id);
    }
}