package com.oscar.bookstoremonolith.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateDTO {
        @NotNull
        @Length(min = 1, max = 100)
        private String username;

        @NotNull
        @Email
        private String email;

        @NotNull
        @Length(min = 5, max = 16)
        private String password;
}
