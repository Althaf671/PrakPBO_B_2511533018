package com.example.Common.Helpers;

import java.text.NumberFormat;
import java.util.Locale;

public final class Formatter {
	public static String formatToRupiah(double nominal) {
		NumberFormat rp = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));
		return "Rp" + rp.format(nominal);
	}
}
