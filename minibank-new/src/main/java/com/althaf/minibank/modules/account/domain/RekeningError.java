package com.althaf.minibank.modules.account.domain;

import com.althaf.minibank.modules.common.abstractions.Error;
import com.althaf.minibank.modules.common.enums.ErrorType;
import static com.althaf.minibank.modules.common.helpers.StringHelper.toWords;

public final class RekeningError {
    private static final String DOMAIN = "Rekening";

    public static final Error NAME_REQUIRED(String field) {
        return Error.create(
            ErrorType.BAD_REQUEST, 
            "NameRequired", 
            toWords(field) + " tidak boleh kosong.", 
            DOMAIN);
    }

    public static final Error BELOW_MIN_LENGTH(String field) {
        return Error.create(
            ErrorType.BAD_REQUEST, 
            "BelowMinLength", 
            "Panjang karakter " + toWords(field) + " tidak boleh lebih pendek dari 3.", 
            DOMAIN);
    } 

    public static final Error EXCEED_MAX_LENGTH(String field) {
        return Error.create(
            ErrorType.BAD_REQUEST, 
            "ExceedMaxLength", 
            "Panjang karakter " + toWords(field) + " tidak boleh lebih pendek dari 50.", 
            DOMAIN);
    }

    public static final Error NO_REKENING_REGISTERED = 
        Error.create(
            ErrorType.NOT_FOUND, 
            "NoRekeningRegistered", 
            "Tidak ada akun terdaftar.", 
            DOMAIN);

    public static final Error NO_REKENING_NOT_FOUND = 
        Error.create(
            ErrorType.NOT_FOUND, 
            "NoRekeningNotFound", 
            "Nomor rekening tidak ditemukan.", 
            DOMAIN);

    public static final Error CANT_CHANGE_TO_CURRENT_AKUN_AKTIF = 
        Error.create(
            ErrorType.DUPLICATED, 
            "CantChangeToCurrentAkunAktif", 
            "Anda sedang menggunakan rekening ini.", 
            DOMAIN);
}
