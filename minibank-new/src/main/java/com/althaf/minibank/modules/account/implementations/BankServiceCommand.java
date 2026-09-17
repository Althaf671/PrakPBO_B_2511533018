package com.althaf.minibank.modules.account.implementations;

import java.util.ArrayList;

import com.althaf.minibank.modules.account.domain.Rekening;
import com.althaf.minibank.modules.account.domain.RekeningError;
import com.althaf.minibank.modules.account.interfaces.IBankServiceCommand;
import com.althaf.minibank.modules.common.abstractions.Result;
import com.althaf.minibank.modules.common.enums.MoneyCurrency;
import static com.althaf.minibank.modules.common.helpers.Formatter.formatCurrency;
import static com.althaf.minibank.modules.common.helpers.Printter.printEmptyLine;
import static com.althaf.minibank.modules.common.helpers.Printter.println;
import static com.althaf.minibank.modules.common.helpers.Printter.writeInput;
import static com.althaf.minibank.modules.common.helpers.ScannerHelper.readDouble;
import static com.althaf.minibank.modules.common.helpers.ScannerHelper.readInt;
import static com.althaf.minibank.modules.common.helpers.ScannerHelper.readString;
import com.althaf.minibank.modules.common.valueObjects.Email;
import com.althaf.minibank.modules.common.valueObjects.Money;

public class BankServiceCommand implements IBankServiceCommand {

    // attributes
    private static Rekening akunAktif = null;
    private static ArrayList<Rekening> listAkun;

    // getter
    public Rekening getAkunAktif() { return akunAktif; }

    // constructor
    public BankServiceCommand() { 
        listAkun = new ArrayList<>();
    }

    // methods
    @Override
    public void bukaRekening() {
        printEmptyLine();
        
        writeInput("Masukan nama depan: ");
        String fr = readString();

        writeInput("Masukan nama belakang: ");
        String ls = readString();

        writeInput("Masukan alamat email: ");
        String em = readString();

        writeInput("Masukan nominal awal: ");
        double noAwal = readDouble();

        writeInput("Pilih mata uang, IDR = 1, USD = 2: ");
        int curr = readInt();

        Result<Email> email = Email.create(em);
        if (email.isFailure()) {
            println(email.getError());
            return;
        }

        MoneyCurrency selectedCurr;
        switch (curr) {
            case 1 -> selectedCurr = MoneyCurrency.IDR;
            case 2 -> selectedCurr = MoneyCurrency.USD;
            default -> {
                println("Pilihan mata uang tidak valid.");
                return;
            }
        }

        Result<Money> money = Money.create(noAwal, selectedCurr);
        if (money.isFailure()) {
            println(money.getError());
            return;
        }

        Result<Rekening> rekening = Rekening.register(fr, ls, money.getValue(), email.getValue());
        if (rekening.isFailure()) {
            println(rekening.getError());
            return;
        }

        Rekening rek = rekening.getValue();
        akunAktif = rek;
        listAkun.add(rek);

        String fullName = rek.getFirstName() + " " + rek.getLastName();
        printEmptyLine();
        println("Sukses membuka rekening atas nama: " + fullName + ".");
    }

    @Override
    public void setorTunai() {
        if (akunAktif == null) {
            println(RekeningError.NO_REKENING_NOT_FOUND);
            return;
        }

        printEmptyLine();
        writeInput("Masukan nominal setor: ");
        double noSetor = readDouble();

        Result<Void> money = akunAktif.setorMoney(noSetor);
        if (money.isFailure()) {
            println(money.getError());
            return;
        }

        printEmptyLine();
        println("Sukses menyetor nominal sebesar " + formatCurrency(noSetor, akunAktif.getSaldo().getCurrency()) + ".");
        println("Total saldo anda adalah " + akunAktif.getSaldo() + ".");
    }

    @Override
    public void tarikTunai() {
        if (akunAktif == null) {
            println(RekeningError.NO_REKENING_NOT_FOUND);
            return;
        }

        printEmptyLine();
        writeInput("Masukan nominal tarik: ");
        double noTarik = readDouble();

        Result<Void> money = akunAktif.tarikMoney(noTarik);
        if (money.isFailure()) {
            println(money.getError());
            return;
        }

        printEmptyLine();
        println("Sukses menarik nominal sebesar " + formatCurrency(noTarik, akunAktif.getSaldo().getCurrency()) + ".");
        println("Sisa saldo anda adalah " + akunAktif.getSaldo() + ".");
    }

    @Override
    public void gantiRekening() {
        if (listAkun.size() <= 0 || listAkun == null) {
            println(RekeningError.NO_REKENING_REGISTERED);
            return;
        }

        printEmptyLine();
        writeInput("Masukan nomor rekening: ");
        String noRek = readString();

        if (noRek.equals(akunAktif.getId())) {
            println(RekeningError.CANT_CHANGE_TO_CURRENT_AKUN_AKTIF);
            return;
        }

        String prevNoRek = akunAktif.getId();
        for (Rekening rek : listAkun) {
            if (rek.getId().equals(noRek)) {
                akunAktif = rek;

                println("Sukses mengganti akun dari " + prevNoRek + " menjadi " + akunAktif.getId() + ".");
                return;
            } else {
                println(RekeningError.NO_REKENING_NOT_FOUND);
                return;
            }
        }
    }

    @Override
    public boolean keluar() {
        printEmptyLine();
        println("Selamat Tinggal!");
        printEmptyLine();
        return false;
    }
}
