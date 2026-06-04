package com.mounisha.bugtracker.dto;

import com.mounisha.bugtracker.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private String name;
    private String email;
    private String password;
    private Role role;
}