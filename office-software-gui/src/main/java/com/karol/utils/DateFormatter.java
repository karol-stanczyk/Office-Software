package com.karol.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static final String DATE_FORMAT = "DD-MM-YYYY";

    public static String toString(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }
}
