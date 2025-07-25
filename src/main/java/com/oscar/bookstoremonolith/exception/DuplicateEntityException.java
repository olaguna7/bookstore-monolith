package com.oscar.bookstoremonolith.exception;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String entityName, String fieldName, Object fieldValue) {
        super(String.format("It already exists a(n) %s with %s = %s", entityName, fieldName, fieldValue));
    }
}
