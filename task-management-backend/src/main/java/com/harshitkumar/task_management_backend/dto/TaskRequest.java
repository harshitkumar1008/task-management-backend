package com.harshitkumar.task_management_backend.dto;

import com.harshitkumar.task_management_backend.entity.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Status is required")
    private Status status;

    @NotBlank(message = "Priority is required")
    private Priority priority;

    @NotBlank(message = "Due date is required")
    private LocalDate dueDate;

    @NotBlank(message = "User id is required")
    private String id;
}

