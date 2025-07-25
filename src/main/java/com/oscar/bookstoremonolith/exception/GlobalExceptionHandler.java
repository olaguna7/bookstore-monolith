package com.oscar.bookstoremonolith.exception;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ApiResponse<Void> handleEntityNotFound(EntityNotFoundException exception) {
        return new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "The requested resource was not found",
                null,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidationError(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error: " + message,
                null,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ApiResponse<Void> handleDuplicateUsername(DuplicateEntityException exception) {
        String message = exception.getMessage();
        return new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                message,
                null,
                LocalDateTime.now()
        );
    }

}
