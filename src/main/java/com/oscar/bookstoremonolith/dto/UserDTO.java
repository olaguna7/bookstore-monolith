package com.oscar.bookstoremonolith.dto;

import java.util.List;

public record UserDTO(
        String username,
        String email,
        List<OrderDTO> orders
) {
}
