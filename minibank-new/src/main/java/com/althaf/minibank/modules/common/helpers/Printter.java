package com.althaf.minibank.modules.common.helpers;

import static com.althaf.minibank.modules.common.helpers.ScannerHelper.readInt;

public final class Printter {
    public static void writeInput(String context) {
        System.out.println(context);
    }

    public static void print(Object context) {
        System.out.println();
        System.out.print(context);
        System.out.println(); 
    }

    public static void println(Object context) {
        System.out.println();
        System.out.println(context);
        System.out.println(); 
    }

    public static void println(String context) {
        System.out.println(context);
    }

    public static void print(String context) {
        System.out.print(context);
    }

    public static void printEmptyLine() {
        System.out.println("");
    } 

    public static int bankTerminal() {
        printEmptyLine();
        println("=== Sistem Perbankan Mini ===");
        println("Menu Utama");
        println("1. Buka rekening baru");
        println("2. Setor tunai");
        println("3. Tarik tunai");
        println("4. Cek informasi rekening");
        println("5. Tampilkan semua rekening");
        println("6. Ganti rekening");
        println("7. Cetak mutasi (riwayat transaksi)");
        println("8. Hitung total nominal riwayat transaksi");
        println("9. Tiga transaksi terakhir");
        println("0. Keluar");
        println("=============================");
        printEmptyLine();

        int pilihan = readInt();
        return pilihan;
    }
}
