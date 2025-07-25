package com.oscar.bookstoremonolith.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorSummaryDTO {
    private Long authorId;
    private String name;
}
