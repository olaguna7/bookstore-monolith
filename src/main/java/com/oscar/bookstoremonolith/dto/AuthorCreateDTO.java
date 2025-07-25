package com.oscar.bookstoremonolith.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthorCreateDTO {
    @NotNull
    String name;
    Date birthday;
    List<Long> booksIds;
}
