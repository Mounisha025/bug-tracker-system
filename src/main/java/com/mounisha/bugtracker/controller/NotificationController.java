package com.mounisha.bugtracker.controller;

import com.mounisha.bugtracker.entity.Notification;
import com.mounisha.bugtracker.entity.User;
import com.mounisha.bugtracker.service.NotificationService;
import com.mounisha.bugtracker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    private final UserService userService;



    public NotificationController(
            NotificationService notificationService,
            UserService userService
    ) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    // Get all notifications of a user
    @GetMapping("/{userId}")
    public List<Notification> getNotifications(
            @PathVariable Long userId
    ) {

        User user =
                userService.getUserEntityById(userId);

        return notificationService.getNotifications(user);

    }

    // Get unread notification count
    @GetMapping("/{userId}/count")
    public long getUnreadCount(
            @PathVariable Long userId
    ) {

        User user =
                userService.getUserEntityById(userId);

        return notificationService.getUnreadCount(user);

    }
    // Mark one notification as read
    @PutMapping("/{id}/read")
    public Notification markAsRead(
            @PathVariable Long id
    ) {

        return notificationService.markAsRead(id);

    }

    // Mark all notifications as read
    @PutMapping("/{userId}/read-all")
    public String markAllRead(
            @PathVariable Long userId
    ) {

        User user =
                userService.getUserEntityById(userId);

        notificationService.markAllAsRead(user);

        return "All notifications marked as read.";

    }

}