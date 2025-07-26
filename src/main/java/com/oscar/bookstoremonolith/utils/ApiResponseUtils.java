package com.oscar.bookstoremonolith.utils;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import com.oscar.bookstoremonolith.dto.ApiResponsePaged;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class ApiResponseUtils {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(500, message, null, LocalDateTime.now());
    }

    public static <T> ApiResponsePaged<T> successPaged(Page<T> page) {
        return new ApiResponsePaged<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

}
