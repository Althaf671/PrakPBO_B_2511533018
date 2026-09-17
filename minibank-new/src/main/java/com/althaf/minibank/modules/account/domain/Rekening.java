package com.althaf.minibank.modules.account.domain;

import static java.lang.System.currentTimeMillis;
import java.util.ArrayList;
import java.util.Map;

import com.althaf.minibank.modules.common.abstractions.EntityBase;
import com.althaf.minibank.modules.common.abstractions.Result;
import static com.althaf.minibank.modules.common.helpers.StringHelper.isNullOrWhitespace;
import com.althaf.minibank.modules.common.valueObjects.Email;
import com.althaf.minibank.modules.common.valueObjects.Money;

public class Rekening extends EntityBase {

    // attributes
    private String firstName;
    private String lastName;
    private Money saldo;
    private Email email;
    private final ArrayList<Transaksi> riwayatTransaksi;

    // getter
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Money getSaldo() { return saldo; }
    public Email getEmail() { return email; }
    public ArrayList<Transaksi> getRiwayatTransaksi() { return riwayatTransaksi; }
    
    // constructor
    private Rekening(String i, String fr, String ls, Money no, Email em) {
        super(i);

        riwayatTransaksi = new ArrayList<>();

        firstName = fr;
        lastName = ls;
        saldo = no;
        email = em;
    }

    // factory
    public static Result<Rekening> register(String fr, String ls, Money no, Email em) {
        Result<Void> validation = validate(fr, ls);
        if (validation.isFailure()) {
            return Result.failure(validation.getError());
        }

        String i = "REK-" + currentTimeMillis();
        return Result.success(new Rekening(i, fr, ls, no, em));
    }

    // behaviour
    public Result<Void> changeName(String fr, String ls) {
        String finalFr = !isNullOrWhitespace(fr)
            ? fr
            : firstName;

        String finalLs = !isNullOrWhitespace(ls)
            ? ls
            : lastName;

        Result<Void> validation = validate(finalFr, finalLs);
        if (validation.isFailure()) {
            return Result.failure(validation.getError());
        }

        firstName = finalFr;
        lastName = finalLs;

        markAsUpdated();

        return Result.success(null);
    }

    public Result<Void> changeEmail(Email em) {
        email = em;

        markAsUpdated();

        return Result.success(null);
    }

    public Result<Void> setorMoney(double no) {
        Result<Money> added = saldo.add(no);
        if (added.isFailure()) {
            return Result.failure(added.getError());
        }

        saldo = added.getValue();

        Result<Money> trxMoney = Money.create(no, saldo.getCurrency());
        if (trxMoney.isFailure()) {
            return Result.failure(trxMoney.getError());
        }

        String fullName = firstName + lastName;
        Result<Transaksi> trx = Transaksi.create(fullName, trxMoney.getValue(), JenisRekening.DEBIT);
        if (trx.isFailure()) {
            return Result.failure(trx.getError());
        }

        riwayatTransaksi.add(trx.getValue());
        return Result.success(null);
    }

    public Result<Void> tarikMoney(double no) {
        Result<Money> subtracted = saldo.sub(no);
        if (subtracted.isFailure()) {
            return Result.failure(subtracted.getError());
        }

        saldo = subtracted.getValue();

        Result<Money> trxMoney = Money.create(no, saldo.getCurrency());
        if (trxMoney.isFailure()) {
            return Result.failure(trxMoney.getError());
        }

        String fullName = firstName + lastName;
        Result<Transaksi> trx = Transaksi.create(fullName, trxMoney.getValue(), JenisRekening.KREDIT);
        if (trx.isFailure()) {
            return Result.failure(trx.getError());
        }

        riwayatTransaksi.add(trx.getValue());
        return Result.success(null);
    }

    // validator
    private static Result<Void> validate(String firstName, String lastName) {
        Map<String, String> fields = Map.of(
            "firstName", firstName,
            "lastName", lastName
        );

        for (var field : fields.entrySet()) {
            String name = field.getKey();
            String value = field.getValue();

            if (isNullOrWhitespace(value)) {
                return Result.failure(RekeningError.NAME_REQUIRED(name));
            }

            if (value.length() < RekeningConstant.MIN_NAME_LENGTH) {
                return Result.failure(RekeningError.BELOW_MIN_LENGTH(name));
            }

            if (value.length() > RekeningConstant.MAX_NAME_LENGTH) {
                return Result.failure(RekeningError.EXCEED_MAX_LENGTH(name));
            } 
        }

        return Result.success(null);
    }
}
