package com.example;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;

import com.example.Common.Enums.CardType;
import com.example.Common.Errors.RekeningError;
import static com.example.Common.Helpers.Formatter.formatToRupiah;
import com.example.Common.Helpers.Result;

public class Rekening {
	
	// attributes
    private String nomorRekening;
    private String namaPemilik;
    private double saldo;
    private final ArrayList<Transaksi> riwayatTransaksi;

    // getter
    public String getNomorRekening() { return nomorRekening; }
    public String getNamaPemilik() { return namaPemilik; }
    public double getSaldo() { return saldo; }

    // setter
    private void setNomorRekening(String nr) { nomorRekening = nr; }
    private void setNamaPemilik(String np) { namaPemilik = np; }
    private void setSaldo(double s) { saldo = s; }

    // constructor
    private Rekening(String nr, String np, double s) {
        riwayatTransaksi = new ArrayList<>();
        nomorRekening = nr;
        namaPemilik = np;
        saldo = s;
        
        out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo " + saldo);
    }

    // factory
    public static Result<Rekening> register(String nr, String np, double s) {
        Result<Void> res = validate(nr, np, s);
        if (res.isFailure()) {
            return Result.failure(res.getError());
        }

        out.println("Rekening dengan ID: " + nr + " Sukses dibuat!");
        return Result.success(new Rekening(nr, np, s));
    }

    // behaviour
    public Result<String> setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;

            String idrTrx = "TRX-S" + currentTimeMillis();
            Result<Transaksi> trxBaru = Transaksi.create(idrTrx, CardType.CREDIT, nominal);
            if (trxBaru.isFailure()) {
                return Result.failure(trxBaru.getError());
            }
            
            riwayatTransaksi.add(trxBaru.getValue());
            return Result.success("[SUCCESS]: \n | Setor tunai dengan nominal: " + formatToRupiah(nominal) + " berhasil. \n | Saldo saat ini: " + formatToRupiah(saldo));
        } else {
            return Result.failure(RekeningError.INVALID_NOMINAL);
        }
    }

    public Result<String> tarikTunai(double nominal) {
    	if (saldo == 10000D) {
    		return Result.failure(RekeningError.BATAS_MINIMUM_SALDO_REACHED);
    	}
        
        if (nominal < 10000D) {
            return Result.failure(RekeningError.CAPAI_BATAS_MINIMAL_NOMINAL);
        }

        if (nominal > saldo) {
            return Result.failure(RekeningError.NOMINAL_LEBIH_BESAR_DARI_SALDO(saldo));
        } 

        saldo -= nominal;

        String idrTrx = "TRX-T" + currentTimeMillis();
        Result<Transaksi> trxBaru = Transaksi.create(idrTrx, CardType.DEBIT, nominal);
        if (trxBaru.isFailure()) {
            return Result.failure(trxBaru.getError());
        }

        riwayatTransaksi.add(trxBaru.getValue());
        return Result.success("[SUCCESS]: \n | Anda menarik saldo " + formatToRupiah(nominal) + " \n | sisa saldo: " + formatToRupiah(saldo));
    }

    public void checkInformasi() {
        out.println("--- Info Rekening ---");
        out.println("No. Rekening: " + nomorRekening);
        out.println("Nama Pemilik: " + namaPemilik);
        out.println("Saldo final:  " + formatToRupiah(saldo));
        out.println("---------------------");
    }
    
	public void cetakMutasi() {
        int no = 1;

    	if (riwayatTransaksi.size() <= 0) {
    		out.println(Result.failure(RekeningError.TIDAK_ADA_RIWAYAT).getError());
    	} 
    	
    	for (Transaksi tx : riwayatTransaksi) {
    		out.println("| " + no + tx.cetakDetail());
            no++;
    	}
	}
	
	public Result<List<String>> hitungRiwayatTotalNominal() {
		double totalDebit = 0;
        double totalCredit = 0;
		
    	if (riwayatTransaksi.size() <= 0) {
    		return Result.failure(RekeningError.TIDAK_ADA_RIWAYAT);
    	}
    	
    	for (Transaksi tx : riwayatTransaksi) {
            if (tx.getJenis() == CardType.CREDIT) {
                double txNominal = tx.getNominal();
                totalCredit += txNominal;            
            }
            if (tx.getJenis() == CardType.DEBIT) {
                double txNominal = tx.getNominal();
                totalDebit += txNominal;
            }
    	}

        String x = String.format(
            "[%s]: %s %s", 
            CardType.CREDIT.toString(), " Sebesar ", formatToRupiah(totalCredit));

        String y = String.format(
            "[%s]: %s %s", 
            CardType.DEBIT.toString(), " Sebesar ", formatToRupiah(totalDebit));

        List<String> z = new ArrayList<>();

        z.add(x);
        z.add(y);
    	
        return Result.success(z);
	}

    public void getLast3() {
        int no = 1;
        int start = Math.max(0, riwayatTransaksi.size() - 3);
        List<Transaksi> threeLast = riwayatTransaksi.subList(start, riwayatTransaksi.size());
        for (Transaksi tx : threeLast) {
            out.println("Tiga transaksi terakhir: ");
            out.println(no + ". " + tx.cetakDetail());
            no++;
        }
    }

    // helper
    private static Result<Void> validate(String nr, String np, double s) {
		if (nr.isEmpty()) {
			return Result.failure(RekeningError.FIELD_CANT_BE_EMPTY(nr));
		}

		if (np.isEmpty()) {
			return Result.failure(RekeningError.FIELD_CANT_BE_EMPTY(np));
		}

		if (s <= 0) {
			return Result.failure(RekeningError.INVALID_NOMINAL);
		} 

        return Result.success(null);
    }

    @Override
    public String toString() {
        return "Nomor rekening: " + nomorRekening + " , Nama pemilik: " + namaPemilik;
    }
}
