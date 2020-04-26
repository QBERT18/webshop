package de.webshop.util;

import java.util.Arrays;

public class StringUtilities {

    public static boolean isAnyNullOrEmpty(final String... strings) {
        return strings == null || Arrays.stream(strings).anyMatch(StringUtilities::isNullOrEmpty);
    }

    public static boolean isNullOrEmpty(final String s) {
        return s == null || s.isEmpty();
    }
}
