package com.android.alarmy_test2.Utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtilities {
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM");
    static DateTimeFormatter dtft = DateTimeFormatter.ofPattern("HH:mm");
    static LocalDateTime now = LocalDateTime.now();

    private static String date = dtf.format(now);
    private static String time = dtft.format(now);
    public static String getDate() {

        return date;
    }

    public static String getTime() {

        return time;
    }
}
