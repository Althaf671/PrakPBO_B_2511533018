package com.althaf.minibank.modules.common.errors;

import com.althaf.minibank.modules.common.abstractions.Error;
import com.althaf.minibank.modules.common.enums.ErrorType;

public final class MoneyError {
    private static final String DOMAIN = "Money";

    public static final Error BELOW_MIN_NOMINAL = 
        Error.create(
            ErrorType.BAD_REQUEST, 
            "BelowMinNominal", 
            "Nominal tidak boleh lebih kecil dari IDR 100 (Seratus Rupiah).", 
            DOMAIN);

    public static final Error EXCEED_MAX_NOMINAL = 
        Error.create(
            ErrorType.BAD_REQUEST, 
            "ExceedMaxNominal", 
            "Nominal tidak boleh lebih besar dari IDR 1.000.000.000.000 (Satu Triliun Rupiah).", 
            DOMAIN);
}
