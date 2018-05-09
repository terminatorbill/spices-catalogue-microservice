package com.spices.service.exception;

public class ProductServiceException extends RuntimeException {
    private final Type type;

    public enum Type {
        PRODUCT_ALREADY_EXISTS,
        CATEGORY_DOES_NOT_EXIST
    }

    public ProductServiceException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public ProductServiceException(String message, Throwable cause, Type type) {
        super(message, cause);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
