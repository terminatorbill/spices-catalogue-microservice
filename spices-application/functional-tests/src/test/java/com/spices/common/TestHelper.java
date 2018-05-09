package com.spices.common;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public final class TestHelper {
    public static final String CATEGORIES_PATH = "http://localhost:8990/categories";
    public static final String ADMIN_CATEGORIES_PATH = "http://localhost:8990/admin/categories";
    public static final String PRODUCTS_PATH = "http://localhost:8990/products";

    private TestHelper() {
    }

    public static String getFileContent(String filename) {
        try {
            return Resources.toString(Resources.getResource(filename), Charsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String generateRandomString(int len) {
        String letters = "abcdefghijklmnopqrstyvw";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(letters.charAt(rnd.nextInt(letters.length())));
        }

        return sb.toString();
    }

    public static Long generateRandomNumber(int bound) {
        Random rnd = new Random();

        return (long) rnd.nextInt(bound) + 1;
    }
}
