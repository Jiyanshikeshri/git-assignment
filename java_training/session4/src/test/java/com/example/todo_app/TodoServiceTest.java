package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import com.example.todo_app.model.Status;

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

    //getTodoById() Test for success
    @Test
    void testGetTodoById_Success() {

        Todo todo = new Todo();
        todo.setTitle("Test Todo");

        when(todoRepository.findById(1L)).thenReturn(java.util.Optional.of(todo));

        Todo result = todoService.getTodoById(1L);

        assertNotNull(result);
        assertEquals("Test Todo", result.getTitle());

        verify(todoRepository, times(1)).findById(1L);
    }

    // getTodoById() Test for exception case
    @Test
    void testGetTodoById_NotFound() {

        // empty result
        when(todoRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Assert the exception
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> todoService.getTodoById(1L)
        );

        // Checking message
        assertTrue(exception.getMessage().contains("Todo not found"));

        // Verify
        verify(todoRepository, times(1)).findById(1L);
    }

    // updateTodo() Test Success case for valid update
    @Test
    void testUpdateTodo_Success() {

        // Existing todo from DB
        Todo existing = new Todo();
        existing.setTitle("Old Title");
        existing.setStatus(Status.PENDING);

        // Updated todo from request
        Todo updated = new Todo();
        updated.setTitle("New Title");
        updated.setStatus(Status.COMPLETED);

        // find by id
        when(todoRepository.findById(1L))
                .thenReturn(java.util.Optional.of(existing));

        // save
        when(todoRepository.save(existing)).thenReturn(existing);

        // calling the service
        Todo result = todoService.updateTodo(1L, updated);

        // Assertions
        assertEquals("New Title", result.getTitle());
        assertEquals(Status.COMPLETED, result.getStatus());

        // Verify
        verify(todoRepository).save(existing);
    }

    // updateTodo() Test Exception case for invalid update
    @Test
    void testUpdateTodo_InvalidStatusTransition() {

        // Existing todo
        Todo existing = new Todo();
        existing.setStatus(Status.PENDING);

        // Invalid update
        Todo updated = new Todo();
        updated.setStatus(Status.IN_PROGRESS);

        // find by id
        when(todoRepository.findById(1L))
                .thenReturn(java.util.Optional.of(existing));

        // Expect exception
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> todoService.updateTodo(1L, updated)
        );

        // Check message
        assertTrue(exception.getMessage().contains("Invalid status"));

        // Verify save
        verify(todoRepository, never()).save(any());
    }
}