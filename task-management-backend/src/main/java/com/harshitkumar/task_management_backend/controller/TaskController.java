package com.harshitkumar.task_management_backend.controller;

import com.harshitkumar.task_management_backend.dto.*;
import com.harshitkumar.task_management_backend.entity.*;
import com.harshitkumar.task_management_backend.service.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/tasks"})
public class TaskController {
    @Autowired
    private TaskService taskService;


    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest task) {
        return ResponseEntity.ok(this.taskService.createTask(task));
    }

    @GetMapping({"/taskId/{taskId}"})
    public ResponseEntity<Task> getTaskById(@PathVariable String taskId) {
        return ResponseEntity.ok(this.taskService.fetchTaskById(Long.parseLong(taskId)));
    }

    @GetMapping({"/userId/{userId}"})
    public ResponseEntity<List<Task>> getTaskByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(this.taskService.fetchTasksByUserId(Long.parseLong(userId)));
    }

    @GetMapping({"/userId/{userId}/status/{status}"})
    public ResponseEntity<List<Task>> getTaskByStatusAndUserId(@PathVariable Status status, @PathVariable String userId) {
        return ResponseEntity.ok(this.taskService.fetchTasksByStatusAndUserId(status, Long.parseLong(userId)));
    }

    @GetMapping({"/userId/{userId}/priority/{priority}"})
    public ResponseEntity<List<Task>> getTaskByUserId(@PathVariable Priority priority, @PathVariable String userId) {
        return ResponseEntity.ok(this.taskService.fetchTaskByPriorityAndUserId(priority, Long.parseLong(userId)));
    }

    @GetMapping({"/userId/{userId}/dueDate/{dueDate}"})
    public ResponseEntity<List<Task>> getTaskByUserId(@PathVariable LocalDate dueDate, @PathVariable String userId) {
        return ResponseEntity.ok(this.taskService.fetchTaskByDueDateAndUserId(dueDate, Long.parseLong(userId)));
    }

    @PutMapping({"/updateTask/{taskId}"})
    public ResponseEntity<Task> updateTaskById(@PathVariable String taskId, @RequestBody @Valid UpdateTaskRequest taskRequest) {
        return ResponseEntity.ok(this.taskService.updateTask(Long.parseLong(taskId), taskRequest));
    }

    @DeleteMapping({"/taskId/{taskId}"})
    public ResponseEntity<String> deleteTaskById(@PathVariable String taskId) {
        this.taskService.deleteTask(Long.parseLong(taskId));
        return ResponseEntity.ok("Task Deleted Successfully.");
    }
}

