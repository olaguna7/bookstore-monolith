package com.oscar.bookstoremonolith.exception;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(EntityNotFoundException exception) {
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "The requested resource was not found",
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationError(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error: " + message,
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateEntity(DuplicateEntityException exception) {
        String message = exception.getMessage();
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                message,
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

}
