package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.ApiResponse;
import com.example.todo.dto.TodoRequest;
import com.example.todo.model.Todo;
import com.example.todo.model.User;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Todo>>> getTodos() {
        User user = getCurrentUser();
        List<Todo> todos = todoRepository.findByUser(user);
        return ResponseEntity.ok(ApiResponse.success("Todos retrieved successfully", todos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Todo>> getTodo(@PathVariable Long id) {
        User user = getCurrentUser();
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        if (!todo.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403)
                    .body(ApiResponse.error("Access denied: You can only access your own todos"));
        }
        return ResponseEntity.ok(ApiResponse.success("Todo retrieved successfully", todo));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Todo>> createTodo(@Valid @RequestBody TodoRequest request) {
        User user = getCurrentUser();
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        todo.setUser(user);
        Todo savedTodo = todoRepository.save(todo);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Todo created successfully", savedTodo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Todo>> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequest request) {
        User user = getCurrentUser();
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        if (!todo.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403)
                    .body(ApiResponse.error("Access denied: You can only update your own todos"));
        }
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        Todo updatedTodo = todoRepository.save(todo);
        return ResponseEntity.ok(ApiResponse.success("Todo updated successfully", updatedTodo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTodo(@PathVariable Long id) {
        User user = getCurrentUser();
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        if (!todo.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403)
                    .body(ApiResponse.error("Access denied: You can only delete your own todos"));
        }
        todoRepository.delete(todo);
        return ResponseEntity.ok(ApiResponse.success("Todo deleted successfully", "Todo with ID " + id + " has been deleted"));
    }
}