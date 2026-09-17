package com.althaf.minibank.bootstrap;

import com.althaf.minibank.modules.account.domain.Rekening;
import com.althaf.minibank.modules.account.implementations.BankServiceCommand;
import com.althaf.minibank.modules.account.implementations.BankServiceQuery;
import static com.althaf.minibank.modules.common.helpers.Printter.bankTerminal;
import static com.althaf.minibank.modules.common.helpers.Printter.println;

/**
 * Boostrapping!
 *
 */
public class App 
{
    private static Rekening akunAktif = null;
    private static boolean isRunning = true;
    
    public static void main(String[] args )
    {
        BankServiceCommand commandService = new BankServiceCommand();
        BankServiceQuery queryService = new BankServiceQuery();

        while (isRunning) {
            int pilihan = bankTerminal();

            switch (pilihan) {
                case 0 -> {
                    boolean status = commandService.keluar();
                    isRunning = status;
                }
                case 1 -> {
                    commandService.bukaRekening();
                    akunAktif = commandService.getAkunAktif();
                    queryService.setAkunAktif(akunAktif);
                    queryService.setRekening(akunAktif);
                } 
                case 2 -> {
                    commandService.setorTunai();
                }
                case 3 -> {
                    commandService.tarikTunai();
                }
                case 4 -> {
                    akunAktif = commandService.getAkunAktif();
                    queryService.setAkunAktif(akunAktif);
                    queryService.cekInformasiRekening();
                }
                case 5 -> {
                    queryService.cekListRekening();
                }
                case 6 -> {
                    commandService.gantiRekening();
                    akunAktif = commandService.getAkunAktif();
                }
                case 7 -> {
                    queryService.cetakMutasi();
                }
                case 8 -> {
                    queryService.hitungTotalNominalRiwayatTransaksi();
                }
                case 9 -> {
                    queryService.cetakTigaTransaksiTerakhir();
                }
                default ->{ 
                    println("Pilihan tidak valid.");
                }
            }
        }
    }
}
