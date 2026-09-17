package com.althaf.minibank.modules.account.implementations;

import java.util.ArrayList;
import java.util.List;

import com.althaf.minibank.modules.account.domain.JenisRekening;
import com.althaf.minibank.modules.account.domain.Rekening;
import com.althaf.minibank.modules.account.domain.RekeningError;
import com.althaf.minibank.modules.account.domain.Transaksi;
import com.althaf.minibank.modules.account.domain.TransaksiError;
import com.althaf.minibank.modules.account.interfaces.IBankServiceQuery;
import static com.althaf.minibank.modules.common.helpers.Formatter.formatCurrency;
import static com.althaf.minibank.modules.common.helpers.Printter.print;
import static com.althaf.minibank.modules.common.helpers.Printter.printEmptyLine;
import static com.althaf.minibank.modules.common.helpers.Printter.println;

public class BankServiceQuery implements IBankServiceQuery {

    // attributes
    private static Rekening akunAktif = null;
    private static ArrayList<Rekening> listAkun;

    // getter-setter
    public Rekening getAkunAktif() { return akunAktif; }
    public void setAkunAktif(Rekening rek) { akunAktif = rek; }
    public void setRekening(Rekening rek) { listAkun.add(rek); }

    // constructor
    public BankServiceQuery() {
        listAkun = new ArrayList<>();
    }

    // methods
    @Override
    public void cekInformasiRekening() {
        if (akunAktif == null) {
            print(RekeningError.NO_REKENING_NOT_FOUND);
            return;
        }

        printEmptyLine();
        println("---- Info Rekening ----");
        println("Nomor Rekening : " + akunAktif.getId());
        println("Nama  Pemilik  : " + akunAktif.getFirstName() + " " + akunAktif.getLastName());
        println("Email Pemilik  : " + akunAktif.getEmail().getAddress());
        println("Saldo Final    : " + akunAktif.getSaldo());
        println("-----------------------");
        printEmptyLine();
    }

    @Override
    public void cekListRekening() {
        if (listAkun.isEmpty() || listAkun == null) {
            println(RekeningError.NO_REKENING_REGISTERED);
            return;
        }

        int no = 1;

        printEmptyLine();
        println("---- Daftar Rekening Anda ----");
        for (Rekening rekening : listAkun) {
            println(no + ". " + rekening.getId());
            no++;
        }
        println("------------------------------");
        printEmptyLine();
    }

    @Override
    public void cetakMutasi() {
        if (akunAktif == null) {
            print(RekeningError.NO_REKENING_NOT_FOUND);
            return;
        }

        int no = 1;
        ArrayList<Transaksi> riwayatTransaksi = akunAktif.getRiwayatTransaksi();
        if (riwayatTransaksi.isEmpty()) {
            println(TransaksiError.NO_TRANSAKSI_EXIST);
            return;
        }

        printEmptyLine();
        println("---- Riwayat Transaksi Anda ----");
        for (Transaksi trx : riwayatTransaksi) {
            println(no + ". " + trx.toString());
            no++;
        }
        println("--------------------------------");
        printEmptyLine();
    }

    @Override
    public void hitungTotalNominalRiwayatTransaksi() {
        if (akunAktif == null) {
            print(RekeningError.NO_REKENING_NOT_FOUND);
            return;
        }

        double totalDebit = 0;
        double totalCredit = 0;

        ArrayList<Transaksi> riwayatTransaksi = akunAktif.getRiwayatTransaksi();
        if (riwayatTransaksi.isEmpty()) {
            println(TransaksiError.NO_TRANSAKSI_EXIST);
            return;
        }

        for (Transaksi trx : riwayatTransaksi) {
            if (trx.getJenisRekening().equals(JenisRekening.DEBIT)) {
                totalDebit += trx.getMoney().getNominal();
            }
            if (trx.getJenisRekening().equals(JenisRekening.KREDIT)) {
                totalCredit += trx.getMoney().getNominal();
            }
        }

        printEmptyLine();
        println("---- Total Nominal Riwayat Transaksi Anda ----");
        println("DEBIT:  " + formatCurrency(totalDebit, akunAktif.getSaldo().getCurrency()));
        println("KREDIT: " + formatCurrency(totalCredit, akunAktif.getSaldo().getCurrency()));
        println("----------------------------------------------");
        printEmptyLine();
    }

    @Override
    public void cetakTigaTransaksiTerakhir() {
        if (akunAktif == null) {
            println(RekeningError.NO_REKENING_NOT_FOUND);
            return;
        }

        ArrayList<Transaksi> riwayatTransaksi = akunAktif.getRiwayatTransaksi();
        int start = Math.max(0, riwayatTransaksi.size() - 3);

        int no = 1;
        List<Transaksi> threeLast = riwayatTransaksi.subList(start, riwayatTransaksi.size());
        if (threeLast.isEmpty()) {
            println(TransaksiError.NO_TRANSAKSI_EXIST);
            return;
        }

        printEmptyLine();
        println("---- Tiga Transaksi Terakhir ----");
        for (Transaksi transaksi : threeLast) {
            println(no + ". " + transaksi.toString());
            no++;
        }
        println("---------------------------------");
        printEmptyLine();
    }
}
