package com.oscar.bookstoremonolith.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
public class OrderCreateDTO {
    @NotNull
    @Length(max = 200)
    private String address;

    @NotNull
    private Long userId;

    @NotNull
    private List<Long> booksIds;
}
