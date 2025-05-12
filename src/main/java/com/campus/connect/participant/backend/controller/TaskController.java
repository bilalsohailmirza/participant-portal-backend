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
import com.campus.connect.participant.backend.payload.request.TaskWithTeamDTO;
import com.campus.connect.participant.backend.payload.request.countTaskByTeam;
import com.campus.connect.participant.backend.payload.request.countTaskBySociety;


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

    // PUT /api/tasks/{taskId}/status - Update task status
    @PutMapping("/updateStatus")
    public ResponseEntity<String> updateTaskStatus(@RequestParam UUID taskId, @RequestParam String status) {

        System.out.println("Task ID: " + taskId); 
        System.out.println("Status: " + status); 

        if (!status.equals("COMPLETED") && !status.equals("PENDING") && !status.equals("IN-PROGRESS")) {
            return ResponseEntity.badRequest().body("Invalid status: Status shall only be COMPLETED, IN-PROGRESS or PENDING");
        }
        int rowsAffected = taskRepository.updateTaskStatus(taskId, status);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("Task status updated successfully");
        } else {
            return ResponseEntity.status(404).body("Task not found");
        }
    }

    // Get all tasks by society id
    @GetMapping("/society")
    public ResponseEntity<List<TaskWithTeamDTO>> getAllTasksBySocietyId(@RequestParam UUID societyId) {
        System.out.println("Society ID: " + societyId); // Debugging line
 
       List<TaskWithTeamDTO> tasks = taskRepository.getAllTasksBySocietyId(societyId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/taskCountByTeam")
    public ResponseEntity<List<countTaskByTeam>> getTaskCountByTeam() {
        List<countTaskByTeam> tasks = taskRepository.getTaskCountByTeam();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/taskCountBySociety")
    public ResponseEntity<List<countTaskBySociety>> getTaskCountBySociety() {
        List<countTaskBySociety> tasks = taskRepository.getTaskCountBySociety();
        return ResponseEntity.ok(tasks);
    }
}

