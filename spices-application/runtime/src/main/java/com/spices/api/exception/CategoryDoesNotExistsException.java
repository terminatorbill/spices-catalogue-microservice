package com.spices.api.exception;

public class CategoryDoesNotExistsException extends RuntimeException {
    public CategoryDoesNotExistsException(String message) {
        super(message);
    }

    public CategoryDoesNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
