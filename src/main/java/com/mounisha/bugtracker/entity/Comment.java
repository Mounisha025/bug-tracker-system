package com.mounisha.bugtracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    private String message;

    private LocalDateTime createdAt;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "bug_id")
    private Bug bug;

    @JsonIgnoreProperties({
            "password"
    })
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}