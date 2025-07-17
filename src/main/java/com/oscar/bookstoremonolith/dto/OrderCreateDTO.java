package com.oscar.bookstoremonolith.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateDTO {
    private String address;
    private Long userId;
    private List<Long> booksIds;
}
