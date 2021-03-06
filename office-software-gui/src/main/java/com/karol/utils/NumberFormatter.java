package com.karol.utils;

import java.text.DecimalFormat;

public class NumberFormatter {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    private static final DecimalFormat INTEGER_FORMAT = new DecimalFormat("#");

    public static String fromDouble(double number) {
        return DECIMAL_FORMAT.format(number);
    }

    public static String fromDoubleToSingleValue(double number) {
        return INTEGER_FORMAT.format(number);
    }

    public static double toDouble(String number) {
        return Double.parseDouble(number);
    }

    public static double fromStringCash(String value) {
        return Double.valueOf(value.replaceAll(",","."));
    }

    public static String toStringCash(double value) {
        return String.valueOf(value).replaceAll("\\.", ",");
    }
}
