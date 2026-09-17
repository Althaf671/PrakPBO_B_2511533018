package com.althaf.minibank.modules.common.helpers;

import java.text.NumberFormat;
import java.util.Locale;

import com.althaf.minibank.modules.common.enums.MoneyCurrency;

public final class Formatter {
	public static String formatCurrency(double nominal, MoneyCurrency currency) {
		Locale locale = currency == MoneyCurrency.IDR
			? Locale.of("id", "ID")
			: Locale.US;

		NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);

		return formatter.format(nominal);
	}
}
