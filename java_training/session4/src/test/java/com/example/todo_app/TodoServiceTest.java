package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TodoServiceTest {

    // Mock repository fake database
    @Mock
    private TodoRepository todoRepository;

    @Mock
    private NotificationServiceClient notificationServiceClient;

    // Inject mock into service
    @InjectMocks
    private TodoService todoService;

    // Initialize mocks before each test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for createTodo()
    @Test
    void testCreateTodo() {

        // Step 1: Create dummy todo object
        Todo todo = new Todo();
        todo.setTitle("Learn Testing");

        // Step 2: Mock repository behavior
        when(todoRepository.save(todo)).thenReturn(todo);

        // Step 3: Calls actual service method
        Todo savedTodo = todoService.createTodo(todo);

        // Step 4: Assertions for verifying result
        assertNotNull(savedTodo);
        assertEquals("Learn Testing", savedTodo.getTitle());

        // Step 5: Verify method call
        verify(todoRepository, times(1)).save(todo);

        // Step 6: Verify notification sent
        verify(notificationServiceClient, times(1))
        .sendNotification(anyString());
    }
}