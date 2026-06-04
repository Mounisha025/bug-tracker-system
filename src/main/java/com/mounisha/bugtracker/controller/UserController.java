package com.mounisha.bugtracker.controller;

import com.mounisha.bugtracker.entity.User;
import com.mounisha.bugtracker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;

import com.mounisha.bugtracker.dto.UserRequestDTO;
import com.mounisha.bugtracker.dto.UserResponseDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUser(
            @RequestBody UserRequestDTO dto) {

        return userService.createUser(dto);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {

        return userService.getUserEntityById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}