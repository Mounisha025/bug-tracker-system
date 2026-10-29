package com.mounisha.bugtracker.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    @Column(length = 1000)
    private String description;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "bug_id")
    private Bug bug;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Activity() {

        this.createdAt = LocalDateTime.now();

    }

    public Long getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Bug getBug() {
        return bug;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}