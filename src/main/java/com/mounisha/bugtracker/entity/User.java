package com.mounisha.bugtracker.entity;

import com.mounisha.bugtracker.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
