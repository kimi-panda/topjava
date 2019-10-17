package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetween(T lt, T startValue, T endValue) {
        if (startValue == null)
            if (endValue == null) return true;
            else return lt.compareTo(endValue) <= 0;
        else {
            boolean b = lt.compareTo(startValue) >= 0;
            if (endValue == null) return b;
            else return b && lt.compareTo(endValue) <= 0;
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

