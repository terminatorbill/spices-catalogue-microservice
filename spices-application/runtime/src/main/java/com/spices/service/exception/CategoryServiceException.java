package com.spices.service.exception;

public class CategoryServiceException extends RuntimeException {
    private final Type type;

    public enum Type {
        DUPLICATE_CATEGORY,
        CATEGORY_DOES_NOT_EXIST,
        CANNOT_DELETE_PARENT_CATEGORY
    }

    public CategoryServiceException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public CategoryServiceException(String message, Throwable cause, Type type) {
        super(message, cause);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
