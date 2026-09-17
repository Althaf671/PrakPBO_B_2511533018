package com.example.Common.Errors;

import com.example.Common.Enums.ErrorType;
import com.example.Common.Helpers.AppError;

public final class RekeningError {
    private static final String DOMAIN = "Rekening";

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

    public static AppError BATAS_MINIMUM_SALDO_REACHED = 
        AppError.failure(
            ErrorType.BAD_REQUEST, 
            "BatasMinimumSaldoDicapai", 
            "Tidak bisa menarik karena ada di batas saldo minimal!", 
            DOMAIN);

    public static AppError CAPAI_BATAS_MINIMAL_NOMINAL = 
        AppError.failure(
            ErrorType.BAD_REQUEST, 
            "CapaiBatasMinimalNominal", 
            "Nominal minimal untuk dimasukan adalah Rp 10.000!", 
            DOMAIN);

    public static AppError NOMINAL_LEBIH_BESAR_DARI_SALDO(double saldo) { 
        return AppError.failure(
            ErrorType.BAD_REQUEST, 
            "NominalLebihBesarDariSaldo", 
            "Nominal tarik lebih besar dari saldo tersedia yaitu: Rp " + saldo + "!", 
            DOMAIN);
    }

    public static AppError TIDAK_ADA_RIWAYAT =
        AppError.failure(
            ErrorType.NOT_FOUND, 
            "TidakAdaRiwayat", 
            "Tidak ada riwayat transaksi terdeteksi.", 
            DOMAIN);
}
