package com.spices.common;

import java.security.SecureRandom;

public final class TestHelpers {
    private TestHelpers() {

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
}
