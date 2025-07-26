package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.*;
import com.oscar.bookstoremonolith.service.OrderService;
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
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    // ---------- GETs --------------

    @GetMapping
    public ResponseEntity<ApiResponse<ApiResponsePaged<UserDTO>>> getAllUsers(@PageableDefault(size = 8) Pageable pageable) {
        Page<UserDTO> users = userService.findAll(pageable);
        ApiResponsePaged<UserDTO> responsePaged = ApiResponseUtils.successPaged(users);
        ApiResponse<ApiResponsePaged<UserDTO>> apiResponse = ApiResponseUtils.success("Users found", responsePaged);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.findById(userId);
        ApiResponse<UserDTO> apiResponse = ApiResponseUtils.success("User found by id", user);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByUsername(@PathVariable String username) {
        UserDTO user = userService.findByUsername(username);
        ApiResponse<UserDTO> apiResponse = ApiResponseUtils.success("User found by username", user);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.findByEmail(email);
        ApiResponse<UserDTO> apiResponse = ApiResponseUtils.success("User found by email", user);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse<ApiResponsePaged<OrderDTO>>> getUserOrders(@PathVariable Long userId, @PageableDefault Pageable pageable) {
        Page<OrderDTO> orders = orderService.findOrdersByUserId(userId, pageable);
        ApiResponsePaged<OrderDTO> responsePaged = ApiResponseUtils.successPaged(orders);
        ApiResponse<ApiResponsePaged<OrderDTO>> apiResponse = ApiResponseUtils.success("Orders found by user", responsePaged);
        return ResponseEntity.ok(apiResponse);
    }

    // ---------- POSTs --------------

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

    // ---------- PUTs --------------

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long userId, @Valid @RequestBody UserCreateDTO userDTO) {
        UserDTO userModified = userService.updateUser(userId, userDTO);
        ApiResponse<UserDTO> apiResponse = ApiResponseUtils.success("User successfully modified", userModified);
        return ResponseEntity.ok(apiResponse);
    }

    // ---------- DELETEs --------------

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "User id=[" + userId + "]successfully deleted",
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }


}
