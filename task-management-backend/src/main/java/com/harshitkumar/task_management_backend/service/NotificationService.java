package com.harshitkumar.task_management_backend.service;

import com.harshitkumar.task_management_backend.entity.*;
import com.harshitkumar.task_management_backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MailService mailService;

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void sendNotifications() {
        System.out.println("Notification Activated");
        HashSet<Notification> pendingNotifications = notificationRepository.findPendingNotifications(LocalDateTime.now(), NotificationStatus.PENDING);
        pendingNotifications.addAll(notificationRepository.findPendingNotificationsForTaskFinishingInAnHour(LocalDateTime.now().plusHours(1), NotificationStatus.PENDING));
        for (Notification notification : pendingNotifications) {

            System.out.println("Notification Activated For Notification: " + notification.getId());
            // Logic to send notification (email, push, etc.)
            if(notification.getTask().getUser().getEmail() != null){
                String emailBody = "Reminder: Complete your task";
                mailService.sendEmail(
                        notification.getTask().getUser().getEmail(),
                        "Task Ending Soon",
                        emailBody,
                        "kumarharshit1008@gmail.com"
                );
            }else{
                // Save notification updates and skip the notification
                notification.setUpdatedAt(LocalDateTime.now());
                notification.setStatus(NotificationStatus.COMPLETED);
                notificationRepository.save(notification);
                continue;
            }


            // Update Notification Status
            if(notification.getRecurrence().equals(Recurrence.NONE)){
                notification.setStatus(NotificationStatus.COMPLETED);
            }else {
                calculateNextReminder(notification);
            }

            // Save notification updates
            notification.setUpdatedAt(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }


    public void endTimeNotificationScheduler(Task task, Notification notification){
        LocalDateTime reminderTime = task.getDueDate().minusHours(1);
        notification.setEndTime(task.getDueDate());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setRecurrence(Recurrence.NONE);
        notification.setReminderTime(reminderTime);

        notificationRepository.save(notification);

        return;
    }


    private void calculateNextReminder(Notification notification){
        if (notification.getRecurrence().equals(Recurrence.DAILY)) {
            notification.setReminderTime(notification.getReminderTime().plusDays(notification.getIntervalValue()));
        } else if (notification.getRecurrence().equals(Recurrence.HOURLY)) {
            notification.setReminderTime(notification.getReminderTime().plusHours(notification.getIntervalValue()));
        } else if(notification.getRecurrence().equals(Recurrence.WEEKLY)){
            notification.setReminderTime(notification.getReminderTime().plusWeeks(notification.getIntervalValue()));
        }

        // End recurring notifications if reminder_time exceeds end_time
        if (notification.getEndTime() != null && notification.getReminderTime().isAfter(notification.getEndTime())) {
            notification.setStatus(NotificationStatus.COMPLETED);
        }
    }
}
