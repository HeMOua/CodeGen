package com.hemou.generator.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat SIMPLE_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss");

    public static String formatDateNumber(Calendar c, int field, int length) {
        return String.format("%0" + length + "d", c.get(field));
    }

    public static String formatDateNumber(int num, int length) {
        return String.format("%0" + length + "d", num);
    }

    public static String formatDate(Calendar c) {
        return DATE_FORMAT.format(c.getTime());
    }

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String formatTime(Calendar c) {
        return TIME_FORMAT.format(c.getTime());
    }

    public static String formatTime(Date date) {
        return TIME_FORMAT.format(date);
    }

    public static String formatSimpleTime(Calendar c) {
        return SIMPLE_TIME_FORMAT.format(c.getTime());
    }

    public static String formatSimpleTime(Date date) {
        return SIMPLE_TIME_FORMAT.format(date);
    }
}
