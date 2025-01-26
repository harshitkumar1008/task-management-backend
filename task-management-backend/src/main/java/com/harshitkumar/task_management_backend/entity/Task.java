package com.harshitkumar.task_management_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;


    @Column(length = 1000)
    private String description;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.TO_DO;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MINOR;

    @Column(name = "dueDate")
    private LocalDateTime dueDate;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}

