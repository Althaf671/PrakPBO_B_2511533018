package com.example.Common.Errors;

import com.example.Common.Enums.ErrorType;
import com.example.Common.Helpers.AppError;

public final class TransaksiError {
    private static final String DOMAIN = "Transaksi";

    public static AppError INVALID_NOMINAL = 
        AppError.failure(
            ErrorType.BAD_REQUEST, 
            "InvalidNominal", 
            "Nominal tidak boleh lebih kecil dari 0!", 
            DOMAIN);

    public static AppError FIELD_CANT_BE_EMPTY(String fieldName) {
        return AppError.failure(
                ErrorType.BAD_REQUEST, 
                "FieldCantBeEmpty", 
                fieldName + " tidak boleh kosong!"
                , DOMAIN);
    } 
}
