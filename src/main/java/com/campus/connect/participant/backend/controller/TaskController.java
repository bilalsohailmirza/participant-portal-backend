package com.campus.connect.participant.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.campus.connect.participant.backend.repository.TaskRepository;
import com.campus.connect.participant.backend.model.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.campus.connect.participant.backend.repository.OrganizerRepository;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final OrganizerRepository organizerRepository;

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository ,
                          OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
        this.taskRepository = taskRepository;
    }

    // POST /api/tasks - Create a task
    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody Task task) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // or extract from authentication.getPrincipal() if needed
        UUID createdBy = organizerRepository.getOrganizerIdByEmail(userId);
        System.out.println("Created By>>>>>>>>>>>>: " + createdBy); // Debugging line
        String organizerId = authentication.getName(); // Assuming this is the user ID
        System.out.println("User ID>>>>>>>>>>>>: " + organizerId); // Debugging line

        task.setTaskId(UUID.randomUUID());
        task.setCreatedBy(createdBy);
        task.setTaskStatus("pending");
        int rowsAffected = taskRepository.createTask(task);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("Task created successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to create task");
        }
    }

    // GET /api/tasks/ - Get tasks created by a user
    @GetMapping("/createdTasks")
    public ResponseEntity<List<Task>> getTasksByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // or extract from authentication.getPrincipal() if needed
        UUID createdBy = organizerRepository.getOrganizerIdByEmail(email);

        List<Task> tasks = taskRepository.getTasksByUserId(createdBy);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/assignedTasks")
    public ResponseEntity<List<Task>> getTasksAssignedToUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // or extract from authentication.getPrincipal() if needed
        UUID createdBy = organizerRepository.getOrganizerIdByEmail(email);

        List<Task> tasks = taskRepository.getTasksAssignedByUserId(createdBy);
        return ResponseEntity.ok(tasks);
    }

    // PUT /api/tasks/{taskId}/complete - Mark task as complete
    @PutMapping("/complete/{taskId}")
    public ResponseEntity<String> markTaskAsComplete(@PathVariable UUID taskId) {
        int rowsAffected = taskRepository.markTaskAsComplete(taskId);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("Task marked as completed");
        } else {
            return ResponseEntity.status(404).body("Task not found");
        }
    }
}

