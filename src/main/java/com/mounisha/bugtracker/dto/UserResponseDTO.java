package com.mounisha.bugtracker.dto;

import com.mounisha.bugtracker.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Role role;
}