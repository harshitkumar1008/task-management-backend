package com.harshitkumar.task_management_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "reminder_time", nullable = false)
    private LocalDateTime reminderTime;

    @Column(name = "recurrence", nullable = false)
    private Recurrence recurrence = Recurrence.NONE;

    @Column(name = "interval_value", nullable = false)
    private Integer intervalValue = 1;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status", nullable = false)
    private NotificationStatus status = NotificationStatus.PENDING;

    @Column(name = "channel", nullable = false)
    private NotificationChannel channel = NotificationChannel.EMAIL;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification notification = (Notification) o;
        return Objects.equals(id, notification.id); // Uniqueness based on id
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
