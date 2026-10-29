package com.mounisha.bugtracker.service;

import com.mounisha.bugtracker.entity.Notification;
import com.mounisha.bugtracker.entity.User;
import com.mounisha.bugtracker.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(
            NotificationRepository notificationRepository
    ) {
        this.notificationRepository = notificationRepository;
    }

    // Create Notification
    public Notification createNotification(
            String message,
            User user
    ) {

        Notification notification = new Notification();

        notification.setMessage(message);

        notification.setUser(user);

        notification.setRead(false);

        return notificationRepository.save(notification);

    }

    // Get User Notifications
    public List<Notification> getNotifications(
            User user
    ) {

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user);

    }

    // Count Unread Notifications
    public long getUnreadCount(
            User user
    ) {

        return notificationRepository
                .countByUserAndIsReadFalse(user);

    }

    // Mark One Notification as Read
    public Notification markAsRead(
            Long id
    ) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElseThrow();

        notification.setRead(true);

        return notificationRepository.save(notification);

    }

    // Mark All Notifications as Read
    public void markAllAsRead(
            User user
    ) {

        List<Notification> notifications =
                notificationRepository
                        .findByUserOrderByCreatedAtDesc(user);

        notifications.forEach(notification ->
                notification.setRead(true));

        notificationRepository.saveAll(notifications);

    }

}