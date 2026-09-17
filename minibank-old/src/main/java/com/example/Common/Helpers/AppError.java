package com.example.Common.Helpers;

import com.example.Common.Enums.ErrorType;

public final class AppError {

    // attributes
    private final ErrorType errorType;
    private final String code;
    private final String description;
    private final String domain;

    // for result success
    public static AppError NONE = new AppError(ErrorType.NONE, "", "", "");

    // getter
    public ErrorType getErrorType() { return errorType; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public String getDomain() { return domain; }

    // constructor
    private AppError(ErrorType errorType, String code, String description, String domain) {
        this.errorType = errorType;
        this.code = code;
        this.description = description;
        this.domain = domain;
    }

    // factory
    public static AppError failure(ErrorType errorType, String code, String description, String domain) {
        return new AppError(errorType, domain + "Errors." + code ,description, domain);
    }

    // helper
    @Override 
    public String toString() {
        System.out.println();
        return String.format(
            "[ERROR] => \n | Code: %s \n | Description: %s \n | Domain: %s", 
            code,
            description,
            domain);
    }
}
