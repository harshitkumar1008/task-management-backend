package com.harshitkumar.task_management_backend.repository;

import com.harshitkumar.task_management_backend.entity.Notification;
import com.harshitkumar.task_management_backend.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.reminderTime <= :currentTime AND n.status = :status")
    HashSet<Notification> findPendingNotifications(@Param("currentTime") LocalDateTime currentTime, @Param("status") NotificationStatus status);

    @Query("SELECT n FROM Notification n WHERE n.reminderTime <= :currentTime AND n.status = :status")
    HashSet<Notification> findPendingNotificationsForTaskFinishingInAnHour(@Param("currentTime") LocalDateTime currentTime,
                                                                        @Param("status") NotificationStatus status);
}
