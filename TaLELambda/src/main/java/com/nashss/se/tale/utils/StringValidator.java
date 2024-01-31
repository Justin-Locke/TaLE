package com.nashss.se.tale.utils;

import java.util.regex.Pattern;

public class StringValidator {
    private static final String PATTERN_REGEX = "^(?!\\s)[\\w\\d\\s]+$";
    private static final Pattern pattern = Pattern.compile(PATTERN_REGEX);

    /**
     * Method to validate String pattern.
     * @param input to be validated
     * @return true or false
     */
    public boolean validateString(String input) {
        return pattern.matcher(input).matches();
    }
}
