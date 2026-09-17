package com.althaf.minibank.modules.common.helpers;

import static java.lang.System.out;
import java.util.Scanner;

public final class ScannerHelper {

    private static final Scanner input = new Scanner(System.in);

    private ScannerHelper() {
    }

    public static String readString() {
        return input.nextLine();
    }

    public static boolean readBool() {
        while (true) {
            String var = input.nextLine();

            if (var.equalsIgnoreCase("true")) {
                return true;
            }

            if (var.equalsIgnoreCase("false")) {
                return false;
            }

            out.println("Input harus berupa boolean.");
        }
    }

    public static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(input.nextLine());
            } catch (NumberFormatException e) {
                out.println("Input harus berupa double.");
            }
        }
    }

    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                out.println("Input harus berupa integer.");
            }
        }
    }
}

