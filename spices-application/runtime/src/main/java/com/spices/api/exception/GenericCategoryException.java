package com.spices.api.exception;

public class GenericCategoryException extends GenericException {
    public GenericCategoryException(String message) {
        super(message);
    }

    public GenericCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
