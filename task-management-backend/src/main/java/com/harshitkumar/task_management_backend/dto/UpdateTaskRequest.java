package com.harshitkumar.task_management_backend.dto;

import com.harshitkumar.task_management_backend.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateTaskRequest {
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDateTime dueDate;
    private String userId;


}

