package com.oscar.bookstoremonolith.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookCreateDTO {
    @NotNull
    private String title;

    @Length(min = 13, max = 13)
    @NotNull
    private String isbn;

    @Length(max = 500)
    private String description;

    @NotNull
    @Min(0)
    private double price;

    @NotNull
    private List<Long> authorsIds;
}
