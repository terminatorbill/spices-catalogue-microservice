package com.spices.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonHelper<T> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonHelper() {

    }

    public static <T> String toString(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> T toObject(String content, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(content, typeReference);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
