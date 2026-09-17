package com.althaf.minibank.modules.common.abstractions;

import com.althaf.minibank.modules.common.enums.ErrorType;

public class Error {
    
    // attributes
    private final ErrorType errorType;
    private final String code;
    private final String description;
    private final String domain;

    // for result success
    public static Error NONE = new Error(ErrorType.NONE, "", "", "");

    // getter
    public ErrorType getErrorType() { return errorType; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public String getDomain() { return domain; }

    // constructor
    private Error(ErrorType errorType, String code, String description, String domain) {
        this.errorType = errorType;
        this.code = code;
        this.description = description;
        this.domain = domain;
    }

    // factory
    public static Error create(ErrorType errorType, String code, String description, String domain) {
        return new Error(errorType, domain + "Errors." + code ,description, domain);
    }

    // helper
    @Override 
    public String toString() {
        System.out.println();
        return String.format(
            "[ERROR]: \n | Code: %s \n | Description: %s \n | Domain: %s", 
            code,
            description,
            domain);
    }
}