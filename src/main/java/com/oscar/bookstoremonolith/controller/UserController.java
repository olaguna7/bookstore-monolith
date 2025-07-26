package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import com.oscar.bookstoremonolith.dto.ApiResponsePaged;
import com.oscar.bookstoremonolith.dto.UserCreateDTO;
import com.oscar.bookstoremonolith.dto.UserDTO;
import com.oscar.bookstoremonolith.service.UserService;
import com.oscar.bookstoremonolith.utils.ApiResponseUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ApiResponsePaged<UserDTO>>> getAllUsers(@PageableDefault(size = 8) Pageable pageable) {
        Page<UserDTO> users = userService.findAll(pageable);
        ApiResponsePaged<UserDTO> responsePaged = ApiResponseUtils.successPaged(users);
        ApiResponse<ApiResponsePaged<UserDTO>> apiResponse = ApiResponseUtils.success("Users found", responsePaged);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserDTO> getUserById(@PathVariable("userId") Long userId) {
        UserDTO user = userService.findById(userId);
        return ApiResponseUtils.success("User found", user);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid @RequestBody UserCreateDTO userDTO) {
        UserDTO userCreated = userService.createUser(userDTO);
        ApiResponse<UserDTO> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "User created",
                userCreated,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


}
