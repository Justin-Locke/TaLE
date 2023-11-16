package com.nashss.se.TaLE.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Pattern;

public class IdUtils {
    private static final Pattern INVALID_CHARACTER_PATTERN = Pattern.compile("[\"'\\\\]");
    static final int MAX_ID_LENGTH = 8;

    private IdUtils() {

    }

    public static String generateActivityId() {
        return "NV" + RandomStringUtils.randomAlphanumeric(MAX_ID_LENGTH);
    }
}
