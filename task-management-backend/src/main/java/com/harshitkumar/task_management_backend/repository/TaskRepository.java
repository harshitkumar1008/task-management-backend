package com.harshitkumar.task_management_backend.repository;

import com.harshitkumar.task_management_backend.entity.*;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findTaskById(long id);

    List<Task> findTasksByUserId(long id);

    List<Task> findTaskByStatusAndUserId(Status status, long id);

    List<Task> findTaskByPriorityAndUserId(Priority priority, long id);

    List<Task> findTaskByDueDateAndUserId(LocalDate dueDate, long id);
}

