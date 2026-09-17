package com.althaf.minibank.modules.account.domain;

import static java.lang.System.currentTimeMillis;

import com.althaf.minibank.modules.common.abstractions.EntityBase;
import com.althaf.minibank.modules.common.abstractions.Result;
import static com.althaf.minibank.modules.common.helpers.Formatter.formatCurrency;
import com.althaf.minibank.modules.common.valueObjects.Money;

public class Transaksi extends EntityBase {
    
    // attributes
    private final String fullName;
    private final Money nominal;
    private final JenisRekening jenisRekening;

    // getter
    public String getFullName() { return fullName; }
    public Money getMoney() { return nominal; }
    public JenisRekening getJenisRekening() { return jenisRekening; }

    // constructor
    private Transaksi(String i, String fl, Money no, JenisRekening jr) {
        super(i);

        fullName = fl;
        nominal = no;
        jenisRekening = jr;
    }

    // factory
    public static Result<Transaksi> create(String fl, Money no, JenisRekening jr) {
        String i = "TRX-" + jr + "-" + currentTimeMillis() + "-" + fl.toLowerCase();
        return Result.success(new Transaksi(i, fl, no, jr));
    }

    // helper
    @Override 
    public String toString() {
        return "Nama: " + fullName + " | Nominal: " + formatCurrency(nominal.getNominal(), nominal.getCurrency()) + " | Jenis Rekening: " + jenisRekening;
    }
}
