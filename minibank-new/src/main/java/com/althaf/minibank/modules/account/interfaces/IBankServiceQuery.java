package com.althaf.minibank.modules.account.interfaces;

public interface IBankServiceQuery {
    public void cekInformasiRekening();
    public void cekListRekening();
    public void cetakMutasi();
    public void hitungTotalNominalRiwayatTransaksi();
    public void cetakTigaTransaksiTerakhir();
}
