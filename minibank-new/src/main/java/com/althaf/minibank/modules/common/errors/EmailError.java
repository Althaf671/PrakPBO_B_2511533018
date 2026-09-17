package com.althaf.minibank.modules.common.errors;

import com.althaf.minibank.modules.common.abstractions.Error;
import com.althaf.minibank.modules.common.enums.ErrorType;

public final class EmailError {
    private static final String DOMAIN = "Email";

    public static final Error EMAIL_REQUIRED = 
        Error.create(
            ErrorType.BAD_REQUEST, 
            "EmailRequired", 
            "Email tidak boleh kosong.", 
            DOMAIN);

    public static final Error BELOW_MIN_LENGTH = 
        Error.create(
            ErrorType.BAD_REQUEST, 
            "BelowMinLength", 
            "Panjang karakter email tidak boleh lebih pendek dari 7.", 
            DOMAIN);

    public static final Error EXCEED_MAX_LENGTH = 
        Error.create(
            ErrorType.BAD_REQUEST, 
            "ExceedMaxLength", 
            "Panjang karakter email tidak boleh lebih pendek dari 255.", 
            DOMAIN);

    public static final Error UNKNOWN_DOMAIN = 
        Error.create(
            ErrorType.BAD_REQUEST, 
            "UnknownDomain", 
            "Domain tidak valid.", 
            DOMAIN);

    public static final Error INVALID_FORMAT = 
        Error.create(
            ErrorType.BAD_REQUEST, 
            "InvalidFormat", 
            "Format email tidak valid.", 
            DOMAIN);
}
