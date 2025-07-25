package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import com.oscar.bookstoremonolith.dto.UserCreateDTO;
import com.oscar.bookstoremonolith.dto.UserDTO;
import com.oscar.bookstoremonolith.service.UserService;
import com.oscar.bookstoremonolith.utils.ApiResponseUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAll();
        return ApiResponseUtils.success("Users found", users);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserDTO> getUserById(@PathVariable("userId") Long userId) {
        UserDTO user = userService.findById(userId);
        return ApiResponseUtils.success("User found", user);
    }

    @PostMapping
    public ApiResponse<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userDTO) {
        UserDTO userCreated = userService.createUser(userDTO);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "User created",
                userCreated,
                LocalDateTime.now()
        );
    }


}
