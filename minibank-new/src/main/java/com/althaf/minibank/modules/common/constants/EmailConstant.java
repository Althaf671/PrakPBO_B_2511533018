package com.althaf.minibank.modules.common.constants;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public final class EmailConstant {
    public static final int MIN_LENGTH = 7;
    public static final int MAX_LENGTH = 255;

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public static final HashSet<String> ALLOWED_DOMAIN = new HashSet<>(
        Set.of(
            "email.com",
            "outlook.com",
            "gmail.com"
        )
    );
}
