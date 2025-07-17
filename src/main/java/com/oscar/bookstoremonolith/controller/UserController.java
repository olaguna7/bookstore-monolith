package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.UserCreateDTO;
import com.oscar.bookstoremonolith.dto.UserDTO;
import com.oscar.bookstoremonolith.entity.User;
import com.oscar.bookstoremonolith.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable("userId") Long userId) {
        return userService.findById(userId);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserCreateDTO userDTO) {
        return userService.createUser(userDTO);
    }


}
