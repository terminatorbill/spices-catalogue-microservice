package com.spices.api.dto;

import java.util.UUID;

public class ErrorDto {
    private ErrorCodeDto errorCode;
    private String description;
    private UUID uuid;

    public ErrorDto() {
    }

    public ErrorDto(ErrorCodeDto errorCode, String description, UUID uuid) {
        this.errorCode = errorCode;
        this.description = description;
        this.uuid = uuid;
    }

    public ErrorCodeDto getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodeDto errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
