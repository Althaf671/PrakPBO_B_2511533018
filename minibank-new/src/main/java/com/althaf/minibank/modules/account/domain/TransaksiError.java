package com.althaf.minibank.modules.account.domain;

import com.althaf.minibank.modules.common.abstractions.Error;
import com.althaf.minibank.modules.common.enums.ErrorType;

public final class TransaksiError {
    private static final String DOMAIN = "Transaksi";

    public static final Error NO_TRANSAKSI_EXIST = 
        Error.create(
            ErrorType.NOT_FOUND, 
            "NoTransaksiExist", 
            "Tidak ada riwayat transaksi.", 
            DOMAIN);
}
