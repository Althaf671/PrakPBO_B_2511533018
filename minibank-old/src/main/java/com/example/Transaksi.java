package com.example;

import static java.lang.System.out;

import com.example.Common.Enums.CardType;
import com.example.Common.Errors.TransaksiError;
import static com.example.Common.Helpers.Formatter.formatToRupiah;
import com.example.Common.Helpers.Result;

public final class Transaksi {

	// attributes
	private final String idTransaksi;
	private final CardType jenis;
	private final double nominal;

	// getter
	public String getIdTransaksi() { return idTransaksi; }
	public CardType getJenis() { return jenis; }
	public double getNominal() { return nominal; }
	
	// constructor
	private Transaksi(String idT, CardType j, double n) {
		idTransaksi = idT;
		jenis = j;
		nominal = n;
		
		out.println("[SUCCESS]: Riwayat transaksi dengan ID: " + idT + " Sukses dibuat!");
	}
	
	// factory
	public static Result<Transaksi> create(String idT, CardType j, double n) {
		Result<Void> res = validate(idT, n);
		if (res.isFailure()) {
			return Result.failure(res.getError());
		}
		return Result.success(new Transaksi(idT, j, n));
	}
	
	// behaviour
	public String cetakDetail() {
		return "ID: " + idTransaksi + " | Jenis: " + jenis + " | Nominal: " + formatToRupiah(nominal);
	}

	public Result<String> getFormattedNominal() {
		if (nominal <= 0) {
			return Result.failure(TransaksiError.INVALID_NOMINAL);
		} 
		return Result.success(formatToRupiah(nominal));
	}

	// validator
	private static Result<Void> validate(String idT, double n) {
		if (idT.isEmpty()) {
			return Result.failure(TransaksiError.FIELD_CANT_BE_EMPTY(idT));
		}

		if (n <= 0) {
			return Result.failure(TransaksiError.INVALID_NOMINAL);
		} 

		return Result.success(null);
	}
}
