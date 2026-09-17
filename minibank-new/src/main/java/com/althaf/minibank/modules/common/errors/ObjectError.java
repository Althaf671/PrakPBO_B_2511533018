package com.althaf.minibank.modules.common.errors;

import com.althaf.minibank.modules.common.abstractions.Error;
import com.althaf.minibank.modules.common.enums.ErrorType;
import static com.althaf.minibank.modules.common.helpers.StringHelper.toWords;

public final class ObjectError {
    public static final Error DOES_NOT_EXISTS(String objectName) {
        return Error.create(
            ErrorType.NOT_FOUND, 
            objectName + "DoesNotExist", 
            toWords(objectName) + " yang dituju tidak ditemukan.", 
            "Unknown");
    } 
}
