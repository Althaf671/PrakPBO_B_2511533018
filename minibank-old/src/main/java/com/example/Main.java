package com.example;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.Common.Helpers.Result;

public class Main {
    private static final ArrayList<Rekening> listAkun = new ArrayList<>();
    private static Rekening akunAktif = null;
    private static boolean isRunning = true;

    // public static void main(String[] args) {
    //     Scanner input = new Scanner(System.in);
    //     bankProcessor(input, isRunning);
    // }

    // procesor
    private static void bankProcessor(Scanner input, boolean isRunning) {
        while (isRunning) {
            printEmptyLine();
            out.println("=== Sistem Perbankan Mini ===");
            showProfil();
            out.println("Menu Utama");
            out.println("1. Buka Rekening Baru");
            out.println("2. Setor Tunai");
            out.println("3. Tarik Tunai");
            out.println("4. Cek Info Rekening");
            out.println("5. Tampilkan semua rekening");
            out.println("6. Ganti akun");
            out.println("7. Cetak Mutasi (riwayat)");
            out.println("8. Hitung total nominal transaksi");
            out.println("9. Get 3 akhir");
            out.println("0. Keluar");
            out.println("=============================");
            printEmptyLine();

            int pilihan = input.nextInt();
            input.nextLine();
            printEmptyLine();

            depositOption(input, pilihan);
        }
    }

    private static void showProfil() {
        if (akunAktif != null) {
            printEmptyLine();
            out.println("Akun saat ini: " + akunAktif.getNomorRekening());
            printEmptyLine();
        }
    }

    // opsi deposit
    private static void depositOption(Scanner input, int pilihan) {
        switch (pilihan) {
            case 1 -> bukaRekening(input);
            case 2 -> setorTunai(input);
            case 3 -> tarikTunai(input);
            case 4 -> checkInformasi();
            case 5 -> cekListAkun();
            case 6 -> gantiAkun(input);
            case 7 -> cetakMutasi();
            case 8 -> hitungTotalNominalRiwayatTransaksi();
            case 9 -> TigaTransaksiTerakhir();
            case 0 -> keluar();
            default -> out.println("Pilihan tidak valid!");
        }
    }

    // create akun
    private static void bukaRekening(Scanner input) {
        out.print("Masukan no rekening: ");
        String no = input.nextLine();

        out.print("Masukan nama pemilik: ");
        String nama = input.nextLine();

        out.print("Masukan saldo awal: ");
        double saldo = input.nextDouble();

        Result<Rekening> ak = Rekening.register(no, nama, saldo);
        if (ak.isFailure()) {
            out.println(ak.getError());
            return;
        }

        akunAktif = ak.getValue();

        listAkun.add(ak.getValue());
    }

    // setor tunai
    private static void setorTunai(Scanner input) {
        for (Rekening rek : listAkun) {
            if (checkKeberadaanAkun(rek)) {
                if (rek.equals(akunAktif)) {
                    out.print("Masukan nominal setor: ");
                    printEmptyLine();

                    double setor = input.nextDouble();
                    Result<String> ak = akunAktif.setorTunai(setor);
                    if (ak.isFailure()) {
                        out.println(ak.getError());
                    }

                    out.println(ak.getValue());
                }
            }
        }
    }

    // tarik tunai
    private static void tarikTunai(Scanner input) {
        for (Rekening rek : listAkun) {
            if (checkKeberadaanAkun(rek)) {
                if (rek.equals(akunAktif)) {
                    out.print("Masukan nominal tarik: ");
                    printEmptyLine();
                    double tarik = input.nextDouble();

                    Result<String> ak = akunAktif.tarikTunai(tarik);
                    if (ak.isFailure()) {
                        out.println(ak.getError());
                    }
                } else {
                    out.println("Akun tidak benar");
                }
            }
        }
    }

    // cek info
    private static void checkInformasi() {
        for (Rekening rek : listAkun) {
            if (checkKeberadaanAkun(rek)) {
                if (rek.equals(akunAktif)) {
                    akunAktif.checkInformasi();  
                }  
            }      
        }
    }

    // ganti akun
    private static void gantiAkun(Scanner input) {
        out.println("Masukan nomor rekening: ");
        printEmptyLine();

        String noRek = input.nextLine();
        for (Rekening rek : listAkun) {
            if (rek == null) {
                out.println("Akun tidak ditemukan");
                return;
            }
            if (rek.getNomorRekening().equals(noRek)) {
                akunAktif = rek;
                out.println("Berhasil ganti akun jadi " + akunAktif.toString());
                return;
            } 
        }
    }

    // tampilkan semua akun
    private static void cekListAkun() {
        int urutan = 1;

        if (listAkun.size() <= 0) {
            out.println("Tidak ada akun terdaftar");
            printEmptyLine();
        }

        for (Rekening rek : listAkun) {
            out.println(urutan + ". " + rek.toString());
            printEmptyLine();

            urutan++;
        }
    }
    
    private static void cetakMutasi() {
        for (Rekening rek : listAkun) {
            if (checkKeberadaanAkun(rek)) {
                if (rek.equals(akunAktif)) {
                    rek.cetakMutasi();
                } 
            }
        }
    }
    
    private static void hitungTotalNominalRiwayatTransaksi() {
        for (Rekening rek : listAkun) {
            if (checkKeberadaanAkun(rek)) {
                if (rek.equals(akunAktif)) {
                    List<String> x = rek.hitungRiwayatTotalNominal().getValue();

                    for (String item : x) {
                        out.println(item);
                    }
                } 
            }
        }	
    }

    private static void TigaTransaksiTerakhir() {
        for (Rekening rek : listAkun) {
            if (checkKeberadaanAkun(rek)) {
                if (rek.equals(akunAktif)) {
                	rek.getLast3();
                } 
            }
        }	
    }

    // close
    private static void keluar() {
        isRunning = false;

        printEmptyLine();
        out.println("Sistem ditutup. Terimakasih!");
        printEmptyLine();
    }

    // cek if akun null
    private static boolean checkKeberadaanAkun(Rekening rek) {
        if (rek == null) {
            out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
            return false;
        }
        return true;
    }

    // empty line
    private static void printEmptyLine() {
        out.println("");
    }
}