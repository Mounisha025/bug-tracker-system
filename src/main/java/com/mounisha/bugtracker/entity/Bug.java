package com.mounisha.bugtracker.entity;

import com.mounisha.bugtracker.enums.BugStatus;
import com.mounisha.bugtracker.enums.Priority;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import java.time.LocalDateTime;

@Entity
@Table(name = "bugs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String screenshot;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private BugStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "reported_by")
    private User reportedBy;

    @JsonManagedReference
    @OneToMany(mappedBy = "bug")
    private List<Comment> comments;
}