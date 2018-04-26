package com.spices.api.exception;

public class CannotDeleteParentCategoryException extends RuntimeException {
    public CannotDeleteParentCategoryException(String message) {
        super(message);
    }

    public CannotDeleteParentCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
