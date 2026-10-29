package com.mounisha.bugtracker.repository;

import com.mounisha.bugtracker.entity.Notification;
import com.mounisha.bugtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {


    List<Notification> findByUserOrderByCreatedAtDesc(
            User user
    );

    long countByUserAndIsReadFalse(
            User user
    );

}