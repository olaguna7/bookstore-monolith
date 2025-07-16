package com.oscar.bookstoremonolith.dto;

public record UserCreateDTO(
        String username,
        String email,
        String password
) {
}
