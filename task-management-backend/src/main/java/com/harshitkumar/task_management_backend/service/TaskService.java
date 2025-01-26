package com.harshitkumar.task_management_backend.service;

import com.harshitkumar.task_management_backend.dto.*;
import com.harshitkumar.task_management_backend.entity.*;
import com.harshitkumar.task_management_backend.repository.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;

    public Task createTask(TaskRequest taskRequest) {
        Optional<User> userOptional = userRepository.findById(Long.parseLong(taskRequest.getId()));
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User Not Found");
        } else {
            User user = userOptional.get();
            Task task = Task.builder().title(taskRequest.getTitle()).description(taskRequest.getDescription()).status(taskRequest.getStatus()).priority(taskRequest.getPriority()).dueDate(taskRequest.getDueDate()).user(user).build();
            task = taskRepository.save(task);
            Notification notification = new Notification();
            notification.setTask(task);
            notificationService.endTimeNotificationScheduler(task, notification);
            return task;
        }
    }

    public List<Task> fetchTasksByUserId(long id) {
        return taskRepository.findTasksByUserId(id);
    }

    public Task fetchTaskById(long id) {
        return taskRepository.findTaskById(id);
    }

    public List<Task> fetchTasksByStatusAndUserId(Status status, long id) {
        return this.taskRepository.findTaskByStatusAndUserId(status, id);
    }

    public List<Task> fetchTaskByPriorityAndUserId(Priority priority, long id) {
        return this.taskRepository.findTaskByPriorityAndUserId(priority, id);
    }

    public List<Task> fetchTaskByDueDateAndUserId(LocalDateTime dueDate, long id) {
        return this.taskRepository.findTaskByDueDateAndUserId(dueDate, id);
    }

    public void deleteTask(long id) {
        this.taskRepository.deleteById(id);
    }

    public Task updateTask(long id, UpdateTaskRequest taskRequest) {
        try {
            Task task = this.taskRepository.findTaskById(id);
            if (taskRequest.getTitle() != null) {
                task.setTitle(taskRequest.getTitle());
            }

            if (taskRequest.getDescription() != null) {
                task.setDescription(taskRequest.getDescription());
            }

            if (taskRequest.getStatus() != null) {
                task.setStatus(taskRequest.getStatus());
            }

            if (taskRequest.getPriority() != null) {
                task.setPriority(taskRequest.getPriority());
            }

            if (taskRequest.getDueDate() != null) {
                task.setDueDate(taskRequest.getDueDate());
            }

            if (taskRequest.getUserId() != null) {
                Optional<User> userOptional = this.userRepository.findById(Long.parseLong(taskRequest.getUserId()));
                if (userOptional.isEmpty()) {
                    throw new RuntimeException("User Not Found");
                }

                User user = (User)userOptional.get();
                task.setUser(user);
            }

            return (Task)this.taskRepository.save(task);
        } catch (Exception var7) {
            throw new RuntimeException("Failed to update the task");
        }
    }
}
