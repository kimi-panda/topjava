package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetween(T lt, T startValue, T endValue) {
        if (startValue == null) {
            if (endValue == null) return true;
            else {
                if (lt.compareTo(endValue) <= 0) return true;
                else return false;
            }
        } else if (endValue == null) {
            if (lt.compareTo(startValue) >= 0) return true;
            else return false;
        } else return lt.compareTo(startValue) >= 0 && lt.compareTo(endValue) <= 0;
    }

    /*public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }*/

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

