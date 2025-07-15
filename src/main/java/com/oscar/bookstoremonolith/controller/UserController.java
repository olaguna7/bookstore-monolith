package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

}
