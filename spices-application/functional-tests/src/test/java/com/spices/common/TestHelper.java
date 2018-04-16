package com.spices.common;

import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public final class TestHelper {
    private static final String BASE_PATH = "http://localhost:8990/categories";
    public static final String CREATE_CATEGORIES_PATH = BASE_PATH;

    private TestHelper() {
    }

    public static String getFileContent(String filename) {
        try {
            return Resources.toString(Resources.getResource(filename), Charsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
