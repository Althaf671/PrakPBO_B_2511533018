package com.althaf.minibank.modules.common.helpers;

import java.util.regex.Pattern;

public final class StringHelper {
    private static final Pattern WORD_BOUNDARY = Pattern.compile("(?<=[a-z0-9])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");

    public static boolean isNullOrWhitespace(String str) {
        if (str == null) {
            return true;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static String toWords(String value) { 
        if (value == null || value.isEmpty()) { 
            return ""; 
        } 
        
        return WORD_BOUNDARY 
            .matcher(value) 
            .replaceAll(" ") 
            .toLowerCase(); 
    }
}
